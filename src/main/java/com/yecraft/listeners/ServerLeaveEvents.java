package com.yecraft.listeners;

import java.util.UUID;

import com.yecraft.bedwars.BedWars;
import com.yecraft.engine.Arena;
import com.yecraft.engine.Team;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class ServerLeaveEvents implements Listener {
	@EventHandler
	public void GameLeave(PlayerQuitEvent e) {
		UUID uuidPlayerEvent = e.getPlayer().getUniqueId();
		Bukkit.getPlayer(uuidPlayerEvent).getPersistentDataContainer().remove(new NamespacedKey(BedWars.getInstance(), "arena"));
		for (Arena arena : Arena.ARENA_MAP.values()) {
			for (UUID uuidPlayerArena : arena.getPlayers()) {
				if (uuidPlayerEvent.equals(uuidPlayerArena)) {
					arena.getPlayers().remove(uuidPlayerEvent);
					arena.getLastPlayerLocation().remove(uuidPlayerEvent);
					for (Team team : arena.getGame().getTeams().values()) {
						for (UUID uuidPlayerTeam : team.getPlayers()) {
							if (uuidPlayerEvent.equals(uuidPlayerTeam)) {
								team.getPlayers().remove(uuidPlayerEvent);
							}
						}
					}
				}
			}
		}
	}
}