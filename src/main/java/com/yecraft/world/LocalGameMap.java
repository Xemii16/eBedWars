package com.yecraft.world;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import com.yecraft.bedwars.BedWars;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;

public class LocalGameMap implements GameMap, Serializable {
	
	private final File sourceWorldFolder;
	private File activeWorldFolder;
	
	private World bukkitWorld;
	
	public LocalGameMap(String worldName, boolean loadOnInit){
		this.sourceWorldFolder = new File(
		BedWars.getInstance().getDataFolder(),
		worldName
		);
		if(loadOnInit) load();
	}

	@Override
	public boolean load() {
		if (isLoaded()) return true;
		
		this.activeWorldFolder = new File (
		Bukkit.getWorldContainer().getParentFile(), 
		sourceWorldFolder.getName() + "_active_" + System.currentTimeMillis()
		);

		try {
			FileUtils.copy(sourceWorldFolder, activeWorldFolder);
		} catch (IOException e){
			Bukkit.getLogger().severe("Failed to load GameMap from source folder" + sourceWorldFolder);
			e.printStackTrace();
			return false;
		}
		Runnable task = () -> {
			this.bukkitWorld = Bukkit.createWorld(
					new WorldCreator(activeWorldFolder.getName())
			);
		};
		Thread thread = new Thread(task);
		thread.setName("WorldLoader");
		thread.start();
		
		if (bukkitWorld !=null) this.bukkitWorld.setAutoSave(false);
		return isLoaded();
	}

	@Override
	public void unload() {
		if (bukkitWorld != null) Bukkit.unloadWorld(bukkitWorld, false);
		if (activeWorldFolder != null) FileUtils.delete(activeWorldFolder);
		
		bukkitWorld = null;
		activeWorldFolder = null;
	}

	@Override
	public boolean restoreFromSource() {
		unload();
		return load();
	}

	@Override
	public boolean isLoaded() {
		return getWorld() != null;
	}

	@Override
	public World getWorld() {
		return bukkitWorld;
	}
	
}