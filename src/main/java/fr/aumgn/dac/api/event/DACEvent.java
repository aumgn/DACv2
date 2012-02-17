package fr.aumgn.dac.api.event;

import org.bukkit.event.Event;

@SuppressWarnings("serial")
public abstract class DACEvent extends Event {

    public DACEvent(String name) {
        super(name);
    }

}
