package com.yecraft.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;

public class CraftEvents implements Listener{
	@EventHandler
	public void CraftCancelEvent (CraftItemEvent e){
		if(!e.getWhoClicked().isOp()){
			e.setCancelled(true);
		}
	}
}