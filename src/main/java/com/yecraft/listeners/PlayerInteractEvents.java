package com.yecraft.listeners;

import com.yecraft.bedwars.BedWars;
import com.yecraft.inventory.JoinInventory;
import com.yecraft.inventory.TeamInventory;

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
		Player player = e.getPlayer();
		if (e.getItem().getType().isAir()) return;
		if (e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase(JoinInventory.TEAM_CHOOSER)){
			new TeamInventory(player).open();
		}
	}
}