package fr.aumgn.dac2.commands.arg;

import fr.aumgn.bukkitutils.command.arg.impl.AbstractCommandArg;
import fr.aumgn.dac2.DAC;
import fr.aumgn.dac2.config.Color;
import fr.aumgn.dac2.exceptions.DACException;

public class ColorArg extends AbstractCommandArg<Color> {

    public static class NotAColor extends DACException {

        private static final long serialVersionUID = 9107450200980690121L;

        public NotAColor(DAC dac, String token) {
            super(dac.getCmdMessages().get("color.arg.notacolor", token));
        }
    }

    private final DAC dac;

    public ColorArg(DAC dac, String string) {
        super(string);
        this.dac = dac;
    }

    @Override
    public Color value() {
        Color color = dac.getColors().get(string);
        if (color == null) {
            throw new NotAColor(dac, string);
        }

        return color;
    }
}
