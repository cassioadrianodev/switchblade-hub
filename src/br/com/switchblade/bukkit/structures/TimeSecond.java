package br.com.switchblade.bukkit.structures;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class TimeSecond extends Event {

	private static HandlerList handlerList = new HandlerList();
	
	public HandlerList getHandlers() {
		return handlerList;
	}

	public static HandlerList getHandlerList() {
		return handlerList;
	}
}