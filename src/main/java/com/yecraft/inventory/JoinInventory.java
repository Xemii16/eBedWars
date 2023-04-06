package com.yecraft.inventory;

import com.yecraft.bedwars.BedWars;
import com.yecraft.engine.Arena;
import com.yecraft.engine.GameStatus;
import com.yecraft.event.GameChangeStatusEvent;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import mc.obliviate.inventory.Gui;
import mc.obliviate.inventory.Icon;

public class JoinInventory extends Gui{
	public JoinInventory (Player player){
		super (player, "join_gui", "Арени", 54);
	}
	
	@Override
	public void onOpen (InventoryOpenEvent event){
		int i = 20;
		for (Arena arena : Arena.ARENA_MAP.values()){
			if (i < 27){
				addItem(i, new Icon(Material.EMERALD_BLOCK).onClick(e -> {
					String name = e.getCurrentItem().getItemMeta().getDisplayName();
					join(Arena.ARENA_MAP.get(name), (Player) e.getWhoClicked());
				}).setName(arena.getName()));
			}
		}
		i++;
	}
	
	public void join (Arena arena, Player player){
		ItemStack itemStack = new ItemStack(Material.RED_BED);
		ItemMeta itemMeta = itemStack.getItemMeta();
		itemMeta.setDisplayName("Вибір команди");
		itemStack.setItemMeta(itemMeta);
		
		player.getInventory().addItem(itemStack);
		player.getInventory().clear();
		arena.getLastPlayerLocation().put(player.getUniqueId(), player.getLocation());
		player.teleport(arena.getMap().getWorld().getSpawnLocation());
		arena.getPlayers().add(player.getUniqueId());
		arena.getBossBar().addPlayer(player);
		player.getPersistentDataContainer().set(new NamespacedKey(BedWars.getInstance(), "arena"), PersistentDataType.STRING, arena.getName());
		
		if (arena.getPlayers().size() >= arena.getMinPlayers()){
			arena.getGame().setGameStatus(GameStatus.START);
			Bukkit.getPluginManager().callEvent(new GameChangeStatusEvent(arena));
		}
	}
}