package fr.aumgn.dac.api.fillstrategy;

import fr.aumgn.dac.api.area.VerticalArea;

/**
 * A strategy responsible for filling a {@link VerticalArea}.
 */
public interface FillStrategy {

    /**
     * Fills the {@link VerticalArea}.
     * 
     * @param area the area to fill
     * @param options optional fill options. 
     */
    void fill(VerticalArea area, String[] options);

}
