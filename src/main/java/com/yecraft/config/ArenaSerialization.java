package com.yecraft.config;

import java.io.File;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yecraft.bedwars.BedWars;

import com.yecraft.engine.Arena;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class ArenaSerialization {
	private static File file;
	private static FileConfiguration config;

	private static void setup(){
		file = new File(Bukkit.getWorldContainer().getParentFile() + File.separator + "arenas");
		if (!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
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

	public static void deserialize(){
		setup();
	}

	public static void serialize(){
		for (Arena arena : Arena.ARENA_MAP.values()){
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			config.addDefault(arena.getName(), gson.toJson(arena));
		}
		config.options().copyDefaults();
	}
}