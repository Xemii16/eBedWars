package com.yecraft.listeners;

import com.yecraft.bedwars.BedWars;
import com.yecraft.inventory.TeamInventory;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.persistence.PersistentDataType;

public class PlayerBlockInteractEvent implements Listener{
	@EventHandler
	public void ChooseTeamClick(PlayerInteractEvent e){
		Player player = e.getPlayer();
		if (player.getPersistentDataContainer().has(new NamespacedKey(BedWars.getInstance(), "arena"), PersistentDataType.STRING)){
			if (e.getItem().getItemMeta() == null) return;
			if (!e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("Вибір команди")) return;
			new TeamInventory(player).open();
		}
	}
}