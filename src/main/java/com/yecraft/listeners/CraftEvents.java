package com.yecraft.listeners;

import com.yecraft.engine.Arena;
import com.yecraft.engine.ArenaUtilities;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;

public class CraftEvents implements Listener{
	@EventHandler
	public void CraftCancelEvent (CraftItemEvent e){
		if (e.getWhoClicked() instanceof Player){
			Player player = (Player) e.getWhoClicked();
			Arena arena = ArenaUtilities.getPlayerArena(player);
			if (arena == null) return;
			if (!player.isOp()){
				e.setCancelled(true);
			}
		}
	}
}