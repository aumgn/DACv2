package fr.aumgn.dac2.config;

public enum PoolReset {
    START,
    END,
    BOTH;

    public int flag() {
        return ordinal() + 1;
    }
}
