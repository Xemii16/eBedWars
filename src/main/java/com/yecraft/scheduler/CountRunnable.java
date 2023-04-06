package com.yecraft.scheduler;

import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CountRunnable extends BukkitRunnable{
	
	private final Set <UUID> players;
	private int number;
	private String subtitle;

	@Override
	public void run() {
		if (this.number == 0){
			this.cancel();
		}
		for (UUID uuid : players){
			Player player = Bukkit.getPlayer(uuid);
			player.sendTitle(String.valueOf(number), subtitle, 1, 18, 1);
		}
		this.number--;
	}
	
}