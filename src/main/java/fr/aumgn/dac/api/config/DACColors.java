package fr.aumgn.dac.api.config;

public interface DACColors extends Iterable<DACColor> {

    DACColor get(String name);

    DACColor defaut();

    int size();

}