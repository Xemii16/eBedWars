package com.yecraft.listeners;

import java.util.UUID;

import com.yecraft.bedwars.BedWars;
import com.yecraft.engine.Arena;
import com.yecraft.engine.Team;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandSendEvent implements Listener{
	@EventHandler
	public void hubEvent(PlayerCommandPreprocessEvent e){
		UUID uuidEvent = e.getPlayer().getUniqueId();
		if (e.getMessage().equalsIgnoreCase("hub")){
			Bukkit.getPlayer(uuidEvent).getPersistentDataContainer().remove(new NamespacedKey(BedWars.getInstance(), "arena"));
			for (Arena arena : Arena.ARENA_MAP.values()){
				if (arena.getPlayers().contains(uuidEvent)){
					arena.getPlayers().remove(uuidEvent);
					arena.getLastPlayerLocation().remove(uuidEvent);
					for (Team team : arena.getGame().getTeams().values()){
                        team.getPlayers().remove(uuidEvent);
					}
				}
			}
		}
	}
}