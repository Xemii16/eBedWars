package com.yecraft.listeners;

import java.util.Objects;
import java.util.UUID;

import com.yecraft.bedwars.BedWars;
import com.yecraft.engine.Arena;
import com.yecraft.engine.ArenaUtilities;
import com.yecraft.engine.Team;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitEvents implements Listener {
	@EventHandler
	public void GameLeave(PlayerQuitEvent e) {
		Arena arena = ArenaUtilities.getPlayerArena(e.getPlayer());
		if (arena == null) return;
		Player player = e.getPlayer();
		ArenaUtilities.removePlayer(arena, player);
		player.teleport(Objects.requireNonNull(Bukkit.getWorld("world")).getSpawnLocation());
	}
}