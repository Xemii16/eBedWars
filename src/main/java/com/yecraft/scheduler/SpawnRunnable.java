package com.yecraft.scheduler;

import java.util.List;

import com.yecraft.engine.Game;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SpawnRunnable extends BukkitRunnable{
	
	private final World world;
	private final List<Location> locations;
	private final Material material;

	@Override
	public void run() {
		for (Location location : locations){
			world.dropItem(location, new ItemStack(material));
		}
	}
	
}