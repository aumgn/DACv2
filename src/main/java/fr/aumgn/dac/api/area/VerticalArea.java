package fr.aumgn.dac.api.area;

import fr.aumgn.dac.api.fillstrategy.FillStrategy;

public interface VerticalArea extends Area {

    public int getTop();

    public int getBottom();

    Iterable<AreaColumn> columns();

    boolean isFull();

    void fillWith(FillStrategy strategy, String[] fillArgs);

}
