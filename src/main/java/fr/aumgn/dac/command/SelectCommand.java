package fr.aumgn.dac.command;

import fr.aumgn.dac.DAC;
import fr.aumgn.dac.arenas.DACArea;
import fr.aumgn.dac.arenas.DACArena;
import fr.aumgn.dac.DACException.InvalidRegionType;
import fr.aumgn.utils.command.PlayerCommandExecutor;

public class SelectCommand extends PlayerCommandExecutor {
	
	@Override
	public boolean onPlayerCommand(Context context, String[] args) {
		if (args.length != 2) { return false; }
		DACArena arena = DAC.getArenas().get(args[0]);
		if (arena == null) {
			context.error("Arène inconnu.");
			return true;
		}
		DACArea area;
		String areaName;
		if (args[1].equalsIgnoreCase("pool")) {
			area = arena.getPool();
			areaName = "La zone du bassin";
		} else if (args[1].equalsIgnoreCase("start")) {
			area = arena.getStartArea();
			areaName = "La zone de départ";
		} else {
			return false;
		}
		try {
			DAC.getWorldEdit().setSelection(context.getPlayer(), area.getSelection());
			context.success(areaName + " a été sélectionné");
			return true;
		} catch (InvalidRegionType exc) {
			context.error("Une erreur est survenu durant la selection de la zone.");
			context.error(exc.getMessage());
			return true;
		}
	}
	
}
