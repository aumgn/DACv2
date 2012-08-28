package fr.aumgn.dac2.arena.regions;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import fr.aumgn.dac2.shape.ArbitraryFlatShape;
import fr.aumgn.dac2.shape.CuboidShape;
import fr.aumgn.dac2.shape.CylinderShape;
import fr.aumgn.dac2.shape.EllipsoidShape;
import fr.aumgn.dac2.shape.PolygonalShape;
import fr.aumgn.dac2.shape.Shape;
import fr.aumgn.dac2.shape.ShapeName;

public class GsonRegionFactory implements TypeAdapterFactory {

    private static final Map<String, Class<? extends Shape>> classes =
            new HashMap<String, Class<? extends Shape>>();

    public static void registerShape(Class<? extends Shape> shapeClass) {
        ShapeName annotation =
                shapeClass.getAnnotation(ShapeName.class);
        if (annotation == null) {
            return;
        }

        classes.put(annotation.value(), shapeClass);
    }

    static {
        registerShape(CuboidShape.class);
        registerShape(CylinderShape.class);
        registerShape(PolygonalShape.class);
        registerShape(EllipsoidShape.class);
        registerShape(ArbitraryFlatShape.class);
    }

    private static class RegionTypeAdapter extends TypeAdapter<Region> {

        private final Gson gson;
        private final Constructor<Region> ctor;

        public RegionTypeAdapter(Gson gson, Constructor<Region> ctor) {
            this.gson = gson;
            this.ctor = ctor;
        }

        @Override
        public Region read(JsonReader reader) throws IOException {
            reader.beginObject();

            reader.nextName();
            String type = reader.nextString();
            Class<? extends Shape> clazz = classes.get(type);
            if (clazz == null) {
                while (reader.peek() != JsonToken.END_OBJECT) {
                    reader.nextName();
                    reader.skipValue();
                }
                reader.endObject();

                return null;
            }

            reader.nextName();
            Shape shape = gson.fromJson(reader, clazz);
            reader.endObject();

            try {
                return ctor.newInstance(shape);
            } catch (IllegalArgumentException _) {
            } catch (InstantiationException _) {
            } catch (IllegalAccessException _) {
            } catch (InvocationTargetException _) {
            }

            return null;
        }

        @Override
        public void write(JsonWriter writer, Region region)
                throws IOException {
            Shape shape = region.getShape();
            ShapeName annotation = shape.getClass()
                    .getAnnotation(ShapeName.class);
            if (annotation == null) {
                writer.nullValue();
                return;
            }
            String shapeName = annotation.value();


            writer.beginObject();
            writer.name("shape");
            writer.value(shapeName);
            writer.name("data");
            gson.toJson(shape, shape.getClass(), writer);
            writer.endObject();
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        if (!Region.class.isAssignableFrom(type.getRawType())) {
            return null;
        }

        try {
            Constructor<Region> ctor = (Constructor<Region>)
                    type.getRawType().getDeclaredConstructor(Shape.class);
            ctor.setAccessible(true);
            return (TypeAdapter<T>) new RegionTypeAdapter(gson, ctor)
                    .nullSafe();
        } catch (SecurityException _) {
        } catch (NoSuchMethodException _) {
        }

        return null;
    }
}
