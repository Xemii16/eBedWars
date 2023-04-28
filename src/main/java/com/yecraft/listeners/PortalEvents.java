package com.yecraft.listeners;

import com.yecraft.engine.Arena;
import com.yecraft.engine.ArenaUtilities;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.PortalCreateEvent;

import java.util.Objects;

public class PortalEvents implements Listener{
	@EventHandler
	public void PortalCancelEvent(PortalCreateEvent e){
		if (!(e.getEntity() instanceof Player)) return;
		Player player = (Player) e.getEntity();
		Arena arena = ArenaUtilities.getPlayerArena(player);
		if (arena == null) return;
		if (!player.isOp()) e.setCancelled(true);
	}
}