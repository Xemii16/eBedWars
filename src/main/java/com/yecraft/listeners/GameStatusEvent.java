package com.yecraft.listeners;

import java.util.UUID;

import com.yecraft.bedwars.BedWars;
import com.yecraft.engine.Arena;
import com.yecraft.engine.Game;
import com.yecraft.engine.GameStatus;
import com.yecraft.engine.Team;
import com.yecraft.event.GameChangeStatusEvent;
import com.yecraft.scheduler.CountRunnable;
import com.yecraft.scheduler.SpawnRunnable;
import com.yecraft.scheduler.CountRunnable;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.boss.BarColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.Material;

import java.util.HashSet;
import java.util.List;

public class GameStatusEvent implements Listener {
	@EventHandler
	public void logicGame(GameChangeStatusEvent e) {
		Arena arena = e.getArena();
		Game game = e.getGame();
		GameStatus status = e.getGameStatus();
		switch (status) {
		case START:
			CountRunnable cr = new CountRunnable(arena.getPlayers(), 5, "скоро");
			cr.runTaskAsynchronously(BedWars.getInstance());
			for (UUID uuid : arena.getPlayers()) {
				Player player = Bukkit.getPlayer(uuid);
				if (!(player.getPersistentDataContainer().getKeys().contains(new NamespacedKey(BedWars.getInstance(), "team")))) {
					for (Team team : arena.getGame().getTeams().values()) {
						if (!(team.getPlayers().size() < arena.getPlayersOnTeam())) return;
						team.getPlayers().add(player.getUniqueId());
					}
				}
			}
			for (Team team : game.getTeams().values()) {
				for (UUID uuid : team.getPlayers()) {
					Player player = Bukkit.getPlayer(uuid);
					player.teleport(team.getSpawn());
					player.sendMessage("Гра почалася за команду " + team.getName());
				}
			}
			game.setGameStatus(GameStatus.ACTIVE);
			Bukkit.getPluginManager().callEvent(new GameChangeStatusEvent(arena));
			break;
		case ACTIVE:
			SpawnRunnable spawnBronze = new SpawnRunnable(game.getMap().getWorld(), game.getBronze(), Material.BRICK);
			spawnBronze.runTaskTimerAsynchronously(BedWars.getInstance(), 0L, game.getBronzeCD());
			SpawnRunnable spawnIron = new SpawnRunnable(game.getMap().getWorld(), game.getIron(), Material.IRON_INGOT);
			spawnIron.runTaskTimerAsynchronously(BedWars.getInstance(), 0L, game.getIronCD());
			SpawnRunnable spawnGold = new SpawnRunnable(game.getMap().getWorld(), game.getGold(), Material.GOLD_INGOT);
			spawnGold.runTaskTimerAsynchronously(BedWars.getInstance(), 0L, game.getGoldCD());
			SpawnRunnable spawnDiamond = new SpawnRunnable(game.getMap().getWorld(), game.getDiamond(), Material.DIAMOND);
			spawnDiamond.runTaskTimerAsynchronously(BedWars.getInstance(), 0L, game.getDiamondCD());
			SpawnRunnable spawnLapis = new SpawnRunnable(game.getMap().getWorld(), game.getLapis(), Material.LAPIS_LAZULI);
			spawnLapis.runTaskTimerAsynchronously(BedWars.getInstance(), 0L, game.getLapisCD());
			arena.getBossBar().setColor(BarColor.GREEN);
			new BukkitRunnable() {
				int time = 3600;

				@Override
				public void run() {
					if (!(game.getGameStatus() == GameStatus.ACTIVE)) {
						this.cancel();
					}
					arena.getBossBar().setTitle(time / 60 + " хв" + time % 60 + " сек");
					arena.getBossBar().setProgress(time / 3600);
					time--;
				}
			} .runTaskTimer(BedWars.getInstance(), 0, 1);
			break;
		case WIN:
			arena.getBossBar().setColor(BarColor.YELLOW);
			arena.getBossBar().setTitle("Гра закінчилася!");
			for (Team team : game.getTeams().values()) {
				if (!(team.getPlayers().isEmpty())) {
					for (UUID uuid : arena.getPlayers()) {
						Player player = Bukkit.getPlayer(uuid);
						player.sendMessage("Перемогла команда " + team.getColor() + team.getName());
					}
					for (UUID uuid : team.getPlayers()) {
						team.getPlayers().remove(uuid);
					}
					game.setGameStatus(GameStatus.RESTART);
					Bukkit.getPluginManager().callEvent(new GameChangeStatusEvent(arena));
				}
			}
			break;
		case RESTART:
			for (UUID uuid : arena.getPlayers()) {
				Player player = Bukkit.getPlayer(uuid);
				arena.getPlayers().remove(uuid);
				player.teleport(arena.getLastPlayerLocation().get(uuid));
				arena.getLastPlayerLocation().remove(uuid);
			}
			for (Team team : game.getTeams().values()) {
				team.setPlayers(new HashSet<>());
			}
			arena.setStatus(false);
			game.getMap().restoreFromSource();
			arena.setStatus(true);
			break;
		}
	}
}