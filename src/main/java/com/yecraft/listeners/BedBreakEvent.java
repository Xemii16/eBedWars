package com.yecraft.listeners;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.yecraft.bedwars.BedWars;
import com.yecraft.engine.Arena;
import com.yecraft.engine.Team;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.persistence.PersistentDataType;

public class BedBreakEvent implements Listener{
	
	private static final Set<Material> BEDS = EnumSet.of(
	Material.BLACK_BED,
	Material.BLUE_BED,
	Material.BROWN_BED,
	Material.CYAN_BED,
	Material.GRAY_BED,
	Material.GREEN_BED,
	Material.LIGHT_BLUE_BED,
	Material.LIGHT_GRAY_BED,
	Material.LIME_BED,
	Material.MAGENTA_BED,
	Material.ORANGE_BED,
	Material.PINK_BED,
	Material.PURPLE_BED,
	Material.RED_BED,
	Material.WHITE_BED,
	Material.YELLOW_BED
	);
	
	@EventHandler
	public void breakBed(BlockBreakEvent e){
		if (BEDS.contains(e.getBlock().getType())){
			if (e.getPlayer().getPersistentDataContainer().has(new NamespacedKey(BedWars.getInstance(), "arena"), PersistentDataType.STRING)){
				Arena arena = Arena.ARENA_MAP.get(e.getPlayer().getPersistentDataContainer().get(new NamespacedKey(BedWars.getInstance(), "arena"), PersistentDataType.STRING));
				for (Team team : arena.getGame().getTeams().values()){
					if (team.getBed().equals(e.getBlock().getType())){
						for (UUID uuid : team.getPlayers()){
							Player player = Bukkit.getPlayer(uuid);
							player.getPersistentDataContainer().set(new NamespacedKey(BedWars.getInstance(), "team"), PersistentDataType.STRING, "false");
						}
						for (UUID uuid : arena.getPlayers()){
							Player player = Bukkit.getPlayer(uuid);
							player.sendMessage("В команди " + team.getName() + " зламали ліжко, співчуваємо!");
						}
					}
				}
			}
		}
	}
}