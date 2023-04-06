package com.yecraft.config;

import java.io.File;
import java.io.IOException;

import com.yecraft.bedwars.BedWars;

import com.yecraft.engine.Arena;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class ArenaSaver {
	private static File file;
	private static FileConfiguration config;

	public static void setup(){
		file = new File(BedWars.getInstance().getDataFolder(), "arena");
		if (!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e){
				e.printStackTrace();
			}
		}
		config = YamlConfiguration.loadConfiguration(file);
	}

	public static FileConfiguration get(){
		return config;
	}

	public static void saveFile(){
		try {
			config.save(file);
		} catch (IOException e){
			e.printStackTrace();
		}
	}

	public static void reload(){
		config = YamlConfiguration.loadConfiguration(file);
	}

	public static void save(){
		for (Arena arena : Arena.ARENA_MAP.values()){

		}
	}
}