package fr.aumgn.dac2.config;

import java.util.Locale;

public class DACConfig {

    private String language = Locale.US.toString();

    public Locale getLocale() {
        String[] splitted = language.split("_");
        if (splitted.length == 0) {
            return Locale.getDefault();
        } else if (splitted.length == 1) {
            return new Locale(splitted[0]);
        } else {
            return new Locale(splitted[0], splitted[1]);
        }
    }
}
