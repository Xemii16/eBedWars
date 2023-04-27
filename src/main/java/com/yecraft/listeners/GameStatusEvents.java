package com.yecraft.listeners;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import com.yecraft.bedwars.BedWars;
import com.yecraft.engine.Arena;
import com.yecraft.engine.Game;
import com.yecraft.engine.GameStatus;
import com.yecraft.engine.Team;
import com.yecraft.event.GameChangeStatusEvent;
import com.yecraft.scheduler.DropResourcesRunnable;

import dev.sergiferry.playernpc.api.NPC;
import dev.sergiferry.playernpc.api.NPCLib;
import org.bukkit.*;
import org.bukkit.boss.BarColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import net.md_5.bungee.api.ChatColor;

public class GameStatusEvents implements Listener {
	@EventHandler
	public void logicGame(GameChangeStatusEvent e) {
		Arena arena = e.getArena();
		Game game = e.getGame();
		GameStatus status = e.getGameStatus();
		switch (status) {
			case WAIT:
				break;
			case START:
				for (UUID uuid : arena.getPlayers()) {
					Player player = Bukkit.getPlayer(uuid);
					if (!(player.getPersistentDataContainer().getKeys().contains(new NamespacedKey(BedWars.getInstance(), "team")))) {
						for (Team team : arena.getGame().getTeams().values()) {
							if (team.getPlayers().size() < arena.getPlayersOnTeam()){
								team.getPlayers().add(player.getUniqueId());
								PersistentDataContainer data = player.getPersistentDataContainer();
								NamespacedKey teamKey = new NamespacedKey(BedWars.getInstance(), "team");
								NamespacedKey bedKey = new NamespacedKey(BedWars.getInstance(), "bed");
								data.set(teamKey, PersistentDataType.STRING, team.getLocalName());
								data.set(bedKey, PersistentDataType.STRING, "true");
							}
						}
					}
				}
				for (Team team : game.getTeams().values()) {
					for (UUID uuid : team.getPlayers()) {
						Player player = Bukkit.getPlayer(uuid);
						player.teleport(team.getSpawn());
						player.sendMessage("Гра почалася за команду " + team.getLocalName());
					}
				}
				game.setGameStatus(GameStatus.ACTIVE);
				Bukkit.getPluginManager().callEvent(new GameChangeStatusEvent(arena));
				break;
			case ACTIVE:

				for (Location location : game.getNpc()){
					NPC.Global npc = NPCLib.getInstance().generateGlobalNPC(BedWars.getInstance(),"trader", location);
					npc.setSkin(
							"ewogICJ0aW1lc3RhbXAiIDogMTY4MTIxOTk2MDg0OCwKICAicHJvZmlsZUlkIiA6ICI1NjY3NWIyMjMyZjA0ZWUwODkxNzllOWM5MjA2Y2ZlOCIsCiAgInByb2ZpbGVOYW1lIiA6ICJUaGVJbmRyYSIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS9iMTdjMzc2YmNlOWJmOTgyNTc4MGM2YjczYjRiMjhiYjM1ZGExN2Y0NjIxY2Y4ZDgxNTZkZDk2ZTVkNzQ5MTMiCiAgICB9CiAgfQp9",
							"HKqTnqeOavHIr9lVKQLRhWEE4kQTpqpCP2ZTbiINbvH3eaP26H3ya6VFRz4SEqNvbGjJaRA0L/DwbPtTDdqT6O9wcIesGRhRtHx8X2KFejfvEXILCR8rTLIGawQ1Y+L6EhshDYzZHPSmHZJwG3tOwE9Ty/VOooPCgPpO2ctDFfa1moS5dfEHpqbQxiLE604fTrhHiszAa/XfCikkZ/nOEtv2uAnADMU7PxiTf79i3Eu7xMuNJaozdG1GP1NUod56Ve/rxUibdyfWZWlFkHyYwN/NJAhLVutQ/5RFOB5CMvWitlQcaYfOiFXafMIriOHT2hwmGZjkaYPcg9+NUk/q0me/v/A1oEuNQypiAd2IPVpgcPxA2XlQI0UyYXJs4KDuB40ig179+3Ik2WnO78Hq1/99dC8dnJqmijBxpLO3rr0PVM8pwIgyH+7hTkCoVEzWfGZJm3wvkKWfRyVJh2AE+bGuLP2iyjsf/qPfSwDxaLGRc72NlEidF+n5E6nTZCJccAegnTknuVPb+koIEAyAHmg3Aq52NqyK3C327C4x6GQCouZs7tCWAemtV8WDTTzRPaRwzqKJQz4ew8jfvblsG7KhSqcn3vzPtzvgKFupBYbliieoR6pdJLZsGIXdTXaEMJhTSCk6tkSaiGBYQ5ehXdczghO3sCLKmTw7un4lfYk="
					);
					npc.setCollidable(true);
					npc.setText("Торговець");
					npc.addRunPlayerCommandClickAction("open");
				}
				BukkitRunnable bossRunnable = new BukkitRunnable() {
					double time = 3600;

					@Override
					public void run() {
						if (game.getGameStatus() != GameStatus.ACTIVE) {
							this.cancel();
							return;
						}
						arena.getBossBar().setTitle(time / 60 + " хв" + time % 60 + " сек");
						arena.getBossBar().setProgress(time / 3600);
						time--;
						if (time == 0){
							game.setGameStatus(GameStatus.DRAW);
							Bukkit.getPluginManager().callEvent(new GameChangeStatusEvent(arena));
						}
					}
				};
				Map<String, DropResourcesRunnable> dropRunnable = new HashMap<>();
				dropRunnable.put("bronze", new DropResourcesRunnable(game.getMap().getWorld(), game.getBronze(), Material.BRICK, game.getBronzeCD()));
				dropRunnable.put("iron", new DropResourcesRunnable(game.getMap().getWorld(), game.getIron(), Material.IRON_INGOT, game.getIronCD()));
				dropRunnable.put("gold", new DropResourcesRunnable(game.getMap().getWorld(), game.getGold(), Material.GOLD_INGOT, game.getGoldCD()));
				dropRunnable.put("diamond", new DropResourcesRunnable(game.getMap().getWorld(), game.getDiamond(), Material.DIAMOND, game.getDiamondCD()));
				dropRunnable.put("lapis", new DropResourcesRunnable(game.getMap().getWorld(), game.getLapis(), Material.LAPIS_LAZULI, game.getLapisCD()));
				game.getDropTasks().putAll(dropRunnable);
				arena.getBossBar().setColor(BarColor.GREEN);


				game.getDropTasks().values().stream()
						.forEach(runnable -> new Thread(() -> {
							boolean bool = true;
							while (bool){
								runnable.runTask(BedWars.getInstance());
								if (game.getGameStatus() != GameStatus.ACTIVE) bool = false;
								try {
									Thread.sleep(runnable.getTime());
								} catch (InterruptedException ex) {
									ex.printStackTrace();
									System.out.println("Drop item error");
								}
							}
						}));
				new Thread (() -> {
					boolean bool = true;
					while (bool){
						bossRunnable.runTask(BedWars.getInstance());
						if (game.getGameStatus() != GameStatus.ACTIVE) bool = false;
						try {
							Thread.sleep(1000);
						} catch (InterruptedException ex) {
							ex.printStackTrace();
							System.out.println("BossBar thread error");
						}
					}
				}).start();
				break;
			case WIN:
				arena.getGame().getDropTasks().values().forEach(DropResourcesRunnable::cancel);
				arena.getBossBar().setColor(BarColor.YELLOW);
				arena.getBossBar().setTitle("Гра закінчилася!");
				for (Team team : game.getTeams().values()) {
					if (!(team.getPlayers().isEmpty())) {
						for (UUID uuid : arena.getPlayers()) {
							Player player = Bukkit.getPlayer(uuid);
							player.sendMessage("Перемогла команда " + ChatColor.of(team.getColor()) + "" + team.getLocalName());
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
				arena.getBossBar().removeAll();
				for (UUID uuid : arena.getPlayers()) {
					Player player = Bukkit.getPlayer(uuid);
					arena.getPlayers().remove(uuid);
					player.teleport(arena.getLastPlayerLocation().get(uuid));
					arena.getLastPlayerLocation().remove(uuid);
				}
				for (Team team : game.getTeams().values()) {
					team.setPlayers(new ArrayList<>());
				}
				arena.setStatus(false);
				game.getMap().restoreFromSource();
				arena.setStatus(true);
				break;
			case DRAW:
		}
	}
}