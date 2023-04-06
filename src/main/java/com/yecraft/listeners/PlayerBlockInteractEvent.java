package com.yecraft.listeners;

import com.yecraft.inventory.TeamInventory;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerBlockInteractEvent implements Listener{
	@EventHandler
	public void ChooseTeamClick(PlayerInteractEvent e){
		Player player = e.getPlayer();
		if (e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("Вибір команди")){
			new TeamInventory(player).open();
		}
	}
}