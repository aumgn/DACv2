package fr.aumgn.dac2.shape.column;

import com.google.common.collect.ImmutableList;
import fr.aumgn.bukkitutils.util.Util;
import fr.aumgn.dac2.config.Color;
import org.bukkit.World;

import java.util.List;

public class RandomPattern implements ColumnPattern {

    private final ColumnPattern[] patterns;

    public RandomPattern(List<ColumnPattern> patterns) {
        this.patterns = patterns.toArray(new ColumnPattern[patterns.size()]);
    }

    public static RandomPattern fromColors(List<Color> colors) {
        ImmutableList.Builder<ColumnPattern> patternsBuilder = ImmutableList.builder();
        for (Color color : colors) {
            patternsBuilder.add(new UniformPattern(color));
        }

        return new RandomPattern(patternsBuilder.build());
    }

    @Override
    public void apply(World world, Column column) {
        int index = Util.getRandom().nextInt(patterns.length);
        patterns[index].apply(world, column);
    }
}
