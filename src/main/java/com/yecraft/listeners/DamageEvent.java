package com.yecraft.listeners;

import java.util.UUID;

import com.yecraft.bedwars.BedWars;
import com.yecraft.engine.Arena;
import com.yecraft.engine.GameStatus;
import com.yecraft.engine.Team;
import com.yecraft.event.GameChangeStatusEvent;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.persistence.PersistentDataType;

public class DamageEvent implements Listener{
	
	@EventHandler
	public void killPlayer(EntityDamageByEntityEvent e){
		Player damager;
		Player player;
		Arena arena;
		if (e.getEntity().getPersistentDataContainer().has(new NamespacedKey(BedWars.getInstance(), "arena"), PersistentDataType.STRING)){
			arena = Arena.ARENA_MAP.get(e.getEntity().getPersistentDataContainer().get(new NamespacedKey(BedWars.getInstance(), "arena"), PersistentDataType.STRING));
		} else return;
		if (e.getDamager().getType().equals(EntityType.PLAYER)){
			damager = (Player) e.getDamager();
		} else return;
		if (e.getEntityType().equals(EntityType.PLAYER)){
			player = (Player) e.getEntity();
		} else return;
		
		if (player.getHealth() <= 0){
			e.setCancelled(true);
			for (UUID uuid : arena.getPlayers()){
				Player players = Bukkit.getPlayer(uuid);
				players.sendMessage("Гравець " + player.getDisplayName() + "вмер через " + damager.getDisplayName());
			}
			if (player.getPersistentDataContainer().get(new NamespacedKey(BedWars.getInstance(), "bed"), PersistentDataType.STRING).equalsIgnoreCase("true")){
				Team team = Arena.ARENA_MAP.get(arena).getGame().getTeams().get(player.getPersistentDataContainer().get(new NamespacedKey(BedWars.getInstance(), "team"), PersistentDataType.STRING));
				player.setHealth(20);
				player.teleport(team.getSpawn());
			} else if (player.getPersistentDataContainer().get(new NamespacedKey(BedWars.getInstance(), "bed"), PersistentDataType.STRING).equalsIgnoreCase("false")) {
				player.getPersistentDataContainer().remove(new NamespacedKey(BedWars.getInstance(), "team"));
				player.getPersistentDataContainer().remove(new NamespacedKey(BedWars.getInstance(), "bed"));
				Team team = arena.getGame().getTeams().get(player.getPersistentDataContainer().get(new NamespacedKey(BedWars.getInstance(), "team"), PersistentDataType.STRING));
				team.getPlayers().remove(player.getUniqueId());
				arena.getPlayers().remove(player.getUniqueId());
				player.setGameMode(GameMode.SPECTATOR);
				player.sendMessage("Ти слилась бебрик, удачі в житті");
			}
			int liveTeams = 0;
			for (Team team : arena.getGame().getTeams().values()){
				if (team.getPlayers().isEmpty()){
					liveTeams++;
				}
			}
			if (liveTeams <= 1){
				arena.getGame().setGameStatus(GameStatus.WIN);
				Bukkit.getPluginManager().callEvent(new GameChangeStatusEvent(arena));
			}
		}
	}
	
}