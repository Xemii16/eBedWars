package com.yecraft.listeners;

import com.yecraft.bedwars.BedWars;
import com.yecraft.engine.Arena;
import com.yecraft.engine.Game;

import com.yecraft.engine.GameStatus;
import com.yecraft.engine.Team;
import com.yecraft.event.GameChangeStatusEvent;
import com.yecraft.scheduler.DeathRunnable;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;
import java.util.Random;
import java.util.UUID;

public class DamageEvents implements Listener{

	List<String> messages = List.of(
			"%s нещасно помер від синдрому Собчука переданого гравцем %s",
			"%s помер від гравця %s",
			"%s бившись до останнього, був переможений гравцем %s",
			"Проклинаючи, гравець %s помер від братчика %s",
			"%s помер від дирявої бібізяни %s",
			"Гравця %s так сильно обійняв %s, що той помер :с"
	);

	public String getRandomDeathMessage(){
		Random random = new Random();
		return messages.get(random.nextInt(messages.size()));
	}

	@EventHandler
	public void damageEvent(EntityDamageEvent e){
		if (e.getCause() == EntityDamageEvent.DamageCause.VOID){
			Player player = (Player) e.getEntity();
			PersistentDataContainer playerData = player.getPersistentDataContainer();
			NamespacedKey arenaKey = new NamespacedKey(BedWars.getInstance(), "arena");
			NamespacedKey teamKey = new NamespacedKey(BedWars.getInstance(), "team");
			NamespacedKey bedKey = new NamespacedKey(BedWars.getInstance(), "bed");
			if (!(playerData.has(arenaKey, PersistentDataType.STRING))) return;
			Arena arena = Arena.ARENA_MAP.get(playerData.get(arenaKey, PersistentDataType.STRING));
			if (!(playerData.has(teamKey, PersistentDataType.STRING))) return;
			Team team = arena.getGame().getTeams().get(playerData.get(teamKey, PersistentDataType.STRING));
			if (!(playerData.has(bedKey, PersistentDataType.STRING))) return;
			if (!(Boolean.parseBoolean(playerData.get(bedKey, PersistentDataType.STRING)))){
				playerData.remove(teamKey);
				playerData.remove(bedKey);
				team.getPlayers().remove(player.getUniqueId());
				int liveTeams = 0;
				for (Team teams : arena.getGame().getTeams().values()) {
					if (teams.getPlayers().size() > 0){
						liveTeams++;
					}
				}
				if (liveTeams == 1){
					arena.getGame().setGameStatus(GameStatus.WIN);
					Bukkit.getPluginManager().callEvent(new GameChangeStatusEvent(arena));
					return;
				}
				player.setGameMode(GameMode.SPECTATOR);
				player.getInventory().clear();
				player.teleport(arena.getGame().getDeathSpawn());
				ItemStack compass = new ItemStack(Material.COMPASS);
				ItemMeta metaCompass = compass.getItemMeta();
				metaCompass.setDisplayName("Слідкувати за гравцем");
				compass.setItemMeta(metaCompass);
				ItemStack torch = new ItemStack(Material.COMPASS);
				ItemMeta metaTorch = compass.getItemMeta();
				metaTorch.setDisplayName("Слідкувати за гравцем");
				torch.setItemMeta(metaCompass);
				player.getInventory().addItem(compass);
				player.getInventory().addItem(torch);
				for (UUID uuid : arena.getPlayers()){
					Player players = Bukkit.getPlayer(uuid);
					assert players != null;
					players.sendMessage(String.format("Гравець %s помер впавши в бескінечність", player.getDisplayName()));
				}
				return;
			}
			arena.getPlayers().stream().map(Bukkit::getPlayer).forEach(players -> {
				assert players != null;
				players.sendMessage(String.format("Гравець %s помер", player.getDisplayName()));
			});
			player.teleport(arena.getGame().getDeathSpawn());
			player.setGameMode(GameMode.SPECTATOR);
			new Thread(() -> {
				DeathRunnable runnable = new DeathRunnable(player, 5, arena.getGame().getDeathSpawn(), arena);
				runnable.runTaskTimer(BedWars.getInstance(), 0L, 20L);
			}).start();
		}
	}

	@EventHandler (priority = EventPriority.HIGHEST)
	public void damageByEntity(EntityDamageByEntityEvent e){
		Player player;
		Player killer;
		if (e.getEntity() instanceof Player & e.getDamager() instanceof Player){
			player = (Player) e.getEntity();
			killer = (Player) e.getDamager();
		}

	}

}