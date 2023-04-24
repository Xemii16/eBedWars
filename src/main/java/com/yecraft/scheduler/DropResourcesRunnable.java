package com.yecraft.scheduler;

import java.io.Serializable;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DropResourcesRunnable extends BukkitRunnable implements Serializable {
	
	private final World world;
	private final List<Location> locations;
	private final Material material;
	private final long time;

	@Override
	public void run() {
		for (Location location : locations){
			world.dropItem(location, new ItemStack(material));
		}
	}

	public long getTime() {
		return time;
	}
}