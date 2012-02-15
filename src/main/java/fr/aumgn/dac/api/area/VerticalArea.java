package fr.aumgn.dac.api.area;

public interface VerticalArea extends Area {

	public int getTop();

	public int getBottom();

	Iterable<AreaColumn> columns();	
	
	void fillWith(AreaFillStrategy areaAllButOneStrategy);

}
