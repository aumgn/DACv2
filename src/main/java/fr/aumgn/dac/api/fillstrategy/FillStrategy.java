package fr.aumgn.dac.api.fillstrategy;

import fr.aumgn.dac.api.area.VerticalArea;

/**
 * A strategy responsible for filling a {@link VerticalArea}.
 */
public interface FillStrategy {

    void fill(VerticalArea area, String[] options);

}
