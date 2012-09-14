package fr.aumgn.dac2.shape.column;

import java.util.List;

import org.bukkit.World;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

import fr.aumgn.bukkitutils.util.Util;
import fr.aumgn.dac2.config.Color;

public class RandomPattern implements ColumnPattern {

    private final ColumnPattern[] patterns;

    public static RandomPattern fromColors(List<Color> colors) {
        return new RandomPattern(Lists.transform(colors,
                new Function<Color, ColumnPattern>() {
                    @Override
                    public ColumnPattern apply(Color color) {
                        return new UniformPattern(color);
                    }
                }));
    }

    public RandomPattern(List<ColumnPattern> patterns) {
        this.patterns = patterns.toArray(new ColumnPattern[patterns.size()]);
    }

    @Override
    public void apply(World world, Column column) {
        int index = Util.getRandom().nextInt(patterns.length);
        patterns[index].apply(world, column);
    }
}
