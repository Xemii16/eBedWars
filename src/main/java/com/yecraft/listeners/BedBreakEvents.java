package com.yecraft.listeners;

import java.util.EnumSet;
import java.util.Set;
import java.util.UUID;

import com.yecraft.bedwars.BedWars;
import com.yecraft.engine.Arena;
import com.yecraft.engine.Team;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class BedBreakEvents implements Listener{

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
	public void breakBed(BlockBreakEvent e) {
		Player player = e.getPlayer();
		Material blockMaterial = e.getBlock().getType();
		PersistentDataContainer playerData = player.getPersistentDataContainer();
		NamespacedKey arenaKey = new NamespacedKey(BedWars.getInstance(), "arena");
		NamespacedKey teamKey = new NamespacedKey(BedWars.getInstance(), "team");
		NamespacedKey bedKey = new NamespacedKey(BedWars.getInstance(), "bed");
		if (!(playerData.has(arenaKey, PersistentDataType.STRING))) return;
		Arena arena = Arena.ARENA_MAP.get(playerData.get(arenaKey, PersistentDataType.STRING));
		if (!(playerData.has(teamKey, PersistentDataType.STRING))) return;
		Team team = arena.getGame().getTeams().get(playerData.get(teamKey, PersistentDataType.STRING));
		if (blockMaterial.equals(team.getBed())){
			e.setCancelled(true);
			player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("Ви не можете зламати своє ліжко!"));
		} else {
			if (BEDS.contains(blockMaterial)){
				e.setDropItems(false);
				for (Team teams : arena.getGame().getTeams().values()){
					if (blockMaterial.equals(teams.getBed())){
						for (UUID uuid : teams.getPlayers()){
							Player teamPlayer = Bukkit.getPlayer(uuid);
							teamPlayer.getPersistentDataContainer().set(bedKey, PersistentDataType.STRING, "false");
						}
						for (UUID uuid : arena.getPlayers()){
							Player arenaPlayer = Bukkit.getPlayer(uuid);
							arenaPlayer.sendMessage(String.format("Ліжко команди %s зламано гравцем %s", team.getLocalName(), player.getDisplayName()));
						}
					}
				}
			}
		}
	}
}