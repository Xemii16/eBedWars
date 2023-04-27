package com.yecraft.listeners;

import com.yecraft.engine.*;

import com.yecraft.scheduler.DeathRunnable;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.List;
import java.util.Objects;
import java.util.Random;

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
			if (e.getEntity() instanceof Player){
				Player player = (Player) e.getEntity();
				Arena arena = ArenaUtilities.getPlayerArena(player);
				if (arena == null) return;
				Team team = ArenaUtilities.getPlayerTeam(arena, player);
				if (team == null) return;
				ArenaUtilities.respawnPlayerOrDoSpectator(arena, team, player);
				ArenaUtilities.sendMessageForPlayersArena(arena.getPlayers(), String.format("Гравець %s впав у бескінечність", player.getDisplayName()));
			}
		}
	}

	@EventHandler (priority = EventPriority.HIGHEST)
	public void damageByEntity(EntityDamageByEntityEvent e){
		Player player;
		Player killer;
		if (e.getEntity() instanceof Player & e.getDamager() instanceof Player) {
			player = (Player) e.getEntity();
			killer = (Player) e.getDamager();
			if (ArenaUtilities.ifPlayerTeammateOfOtherPlayer(player, killer)){
				e.setCancelled(true);
				killer.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("Ви не можете вдарити свого тімейта"));
				return;
			}
			Arena arena = ArenaUtilities.getPlayerArena(player);
			if (arena == null) return;
			Team team = ArenaUtilities.getPlayerTeam(arena, player);
			if (team == null) return;
			if (player.getHealth() <= 0){
				ArenaUtilities.sendMessageForPlayersArena(arena.getPlayers(), String.format(getRandomDeathMessage(), player.getDisplayName(), killer.getDisplayName()));
				ArenaUtilities.respawnPlayerOrDoSpectator(arena, team, player);
			}
		} else {
			if (e.getEntity() instanceof Player){
				player = (Player) e.getEntity();
				Entity damager = e.getDamager();
				Arena arena = ArenaUtilities.getPlayerArena(player);
				if (arena == null) return;
				Team team = ArenaUtilities.getPlayerTeam(arena, player);
				if (player.getHealth() <= 0){
					ArenaUtilities.sendMessageForPlayersArena(arena.getPlayers(), String.format("Гравець %s помер від %s", player.getDisplayName(), damager.getName()));
					ArenaUtilities.respawnPlayerOrDoSpectator(arena, team, player);
				}
			}
		}
	}
}