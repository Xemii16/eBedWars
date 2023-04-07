package com.yecraft.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.yecraft.bedwars.BedWars;
import com.yecraft.engine.Arena;
import org.bukkit.Bukkit;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

public class ArenaSerialization {
	private static File file;

	public static void setup(){
		file = new File(Bukkit.getWorldContainer().getParentFile() + File.separator + "arenas");
		if (!file.exists()){
			file.mkdir();
		}
	}

	public static boolean deserialize(){
		File arenasFolder = file = new File(BedWars.getInstance().getDataFolder().getParentFile() + "/arenas");
		if (arenasFolder.list() == null) return false;
		for (File file : arenasFolder.listFiles()){
			try {
				FileInputStream fileInputStream = new FileInputStream(file);
				BukkitObjectInputStream objectInputStream = new BukkitObjectInputStream(fileInputStream);
				Arena arena = (Arena) objectInputStream.readObject();
				Arena.ARENA_MAP.put(arena.getName(), arena);
				objectInputStream.close();
			} catch (Exception e){
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}

	public static boolean serialize(){
		File arenasFolder = file = new File(BedWars.getInstance().getDataFolder().getParentFile() + "/arenas");
		for (Arena arena : Arena.ARENA_MAP.values()){
			try {
				FileOutputStream fileOutputStream = new FileOutputStream(arenasFolder + File.separator + arena.getName());
				BukkitObjectOutputStream objectOutputStream = new BukkitObjectOutputStream(fileOutputStream);
				objectOutputStream.writeObject(arena);
				objectOutputStream.close();
			} catch (Exception e){
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}
}