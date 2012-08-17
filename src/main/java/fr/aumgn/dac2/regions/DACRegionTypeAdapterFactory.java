package fr.aumgn.dac2.regions;

import java.io.IOException;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public class DACRegionTypeAdapterFactory implements TypeAdapterFactory {

    public static class DACRegionTypeAdapter<T> extends TypeAdapter<T> {

        public DACRegionTypeAdapter() {
        }

        @Override
        public T read(JsonReader reader) throws IOException {
            return null;
        }

        @Override
        public void write(JsonWriter writer, T obj) throws IOException {
        }
    }

    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> token) {
        if (!DACRegion.class.isAssignableFrom(
                token.getRawType())) {
            return null;
        }

        return new DACRegionTypeAdapter<T>();
    }
}
