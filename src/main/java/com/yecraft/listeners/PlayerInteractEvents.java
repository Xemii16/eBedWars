package com.yecraft.listeners;

import com.yecraft.bedwars.BedWars;
import com.yecraft.engine.Arena;
import com.yecraft.engine.ArenaUtilities;
import com.yecraft.inventory.JoinInventory;
import com.yecraft.inventory.TeamInventory;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.persistence.PersistentDataType;

import java.util.Objects;

public class PlayerInteractEvents implements Listener{
	@EventHandler
	public void ChooseTeamClick(PlayerInteractEvent e){
		if (e.getItem() == null) return;
		Arena arena = ArenaUtilities.getPlayerArena(e.getPlayer());
		if (arena == null) return;
		Player player = e.getPlayer();
		if (Objects.requireNonNull(e.getItem().getItemMeta()).getDisplayName().equalsIgnoreCase(JoinInventory.TEAM_CHOOSER)){
			new TeamInventory(player).open();
		}
		if (e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase(JoinInventory.LEAVE_BUTTON)){
			ArenaUtilities.removePlayer(arena, player);
			player.teleport(Objects.requireNonNull(Bukkit.getWorld("world")).getSpawnLocation());
		}
	}
}