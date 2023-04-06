package com.yecraft.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.PortalCreateEvent;

public class PortalEvent implements Listener{
	@EventHandler
	public void PortalCancelEvent(PortalCreateEvent e){
		if (!e.getEntity().isOp()){
			e.setCancelled(true);
		}
	}
}