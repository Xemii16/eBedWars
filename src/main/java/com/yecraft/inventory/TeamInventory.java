package com.yecraft.inventory;

import com.yecraft.bedwars.BedWars;
import com.yecraft.engine.Arena;
import com.yecraft.engine.Game;
import com.yecraft.engine.Team;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.persistence.PersistentDataType;

import mc.obliviate.inventory.Gui;
import mc.obliviate.inventory.Icon;

public class TeamInventory extends Gui{
	
	public TeamInventory(Player player){
		super (player, "team_inventory", "Вибір команди", 9);
	}
	
	@Override
	public void onOpen(InventoryOpenEvent event){
		Game game = Arena.ARENA_MAP.get(player.getPersistentDataContainer().get(new NamespacedKey(BedWars.getInstance(), "arena"), PersistentDataType.STRING)).getGame();
		Arena arena = Arena.ARENA_MAP.get(player.getPersistentDataContainer().get(new NamespacedKey(BedWars.getInstance(), "arena"), PersistentDataType.STRING));
		int i = 0;
		for (Team team : game.getTeams().values()){
			addItem(i, new Icon(team.getBed()).onClick(e -> {
				String name = e.getCurrentItem().getItemMeta().getDisplayName();
				if (game.getTeams().get(name).getPlayers().size() < arena.getPlayersOnTeam()){
					game.getTeams().get(name).getPlayers().add(player.getUniqueId());
					player.getPersistentDataContainer().set(new NamespacedKey(BedWars.getInstance(), "bed"), PersistentDataType.STRING, "true");
					player.getPersistentDataContainer().set(new NamespacedKey(BedWars.getInstance(), "team"), PersistentDataType.STRING, team.getName());
				}
			}).setName(team.getName()));
		}
		i++;
		
	}
	
}