package com.yecraft.listeners;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import com.yecraft.bedwars.BedWars;
import com.yecraft.engine.*;
import com.yecraft.event.GameChangeStatusEvent;
import com.yecraft.scheduler.BossBarRunnable;
import com.yecraft.scheduler.DropResourcesRunnable;

import com.yecraft.scheduler.SpawnerThread;
import dev.sergiferry.playernpc.api.NPC;
import dev.sergiferry.playernpc.api.NPCLib;
import org.bukkit.*;
import org.bukkit.boss.BarColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

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
				arena.getPlayers().stream()
						.map(Bukkit::getPlayer)
						.filter(Objects::nonNull)
						.forEach(player -> {
							if (!ArenaUtilities.ifPlayerInTeam(arena, player)){
								ArenaUtilities.addPlayerToFreeTeam(arena, player);
							}
						});
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
				arena.getBossBar().setColor(BarColor.GREEN);
				new DropResourcesRunnable(game.getMap().getWorld(), game.getBronze(), Material.BRICK, game.getBronzeCD()).runTaskTimer(BedWars.getInstance(), 0, game.getBronzeCD());
				new DropResourcesRunnable(game.getMap().getWorld(), game.getIron(), Material.IRON_INGOT, game.getIronCD()).runTaskTimer(BedWars.getInstance(), 0, game.getIronCD());
				new DropResourcesRunnable(game.getMap().getWorld(), game.getGold(), Material.GOLD_INGOT, game.getGoldCD()).runTaskTimer(BedWars.getInstance(), 0, game.getGoldCD());
				new DropResourcesRunnable(game.getMap().getWorld(), game.getDiamond(), Material.DIAMOND, game.getDiamondCD()).runTaskTimer(BedWars.getInstance(), 0, game.getDiamondCD());
				new DropResourcesRunnable(game.getMap().getWorld(), game.getLapis(), Material.LAPIS_LAZULI, game.getLapisCD()).runTaskTimer(BedWars.getInstance(), 0, game.getLapisCD());
				new Thread(() -> {
					boolean bool = true;
					int time = 3600;
					while (bool){
						new BossBarRunnable(arena, time).run();
						if (game.getGameStatus() != GameStatus.ACTIVE) bool = false;
						time--;
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
							player.sendMessage("Перемогла команда " + ChatColor.of(team.getColor()) + team.getLocalName());
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