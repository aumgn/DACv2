package fr.aumgn.dac.api.fillstrategy;

import java.util.List;

import fr.aumgn.dac.api.area.VerticalArea;

/**
 * A strategy responsible for filling a {@link VerticalArea}.
 */
public interface FillStrategy {

    void fill(VerticalArea area, List<String> args);

}
