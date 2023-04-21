package com.yecraft.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import com.yecraft.bedwars.BedWars;
import com.yecraft.engine.Arena;
import com.yecraft.engine.GameStatus;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

public class ArenaStorage {
	private static File file;

	public static void setup(){
		file = new File(Bukkit.getWorldContainer().getParentFile() + File.separator + "arenas");
		if (!file.exists()){
			file.mkdir();
		}
	}

	public static boolean deserialize(){
		File arenasFolder = new File(BedWars.getInstance().getDataFolder().getParentFile() + "/arenas");
		if (arenasFolder.list() == null) return false;
		for (File file : arenasFolder.listFiles()){
			try {
				FileInputStream fileInputStream = new FileInputStream(file);
				BukkitObjectInputStream objectInputStream = new BukkitObjectInputStream(fileInputStream);
				Arena arena = (Arena) objectInputStream.readObject();
				arena.setStatus(false);
				Arena.ARENA_MAP.put(arena.getName(), arena);
				objectInputStream.close();
			} catch (Exception e){
				e.printStackTrace();
				return false;
			}
		}
		for (Arena arena : Arena.ARENA_MAP.values()){
			World lobbyMap = arena.getMap().getWorld();
			World gameMap = arena.getMap().getWorld();
			arena.getSpawn().setWorld(lobbyMap);
			arena.getGame().getDeathSpawn().setWorld(gameMap);
			for (Location location : arena.getGame().getNpc()){
				location.setWorld(gameMap);
			}
			for (Location location : arena.getGame().getBronze()){
				location.setWorld(gameMap);
			}
			for (Location location : arena.getGame().getIron()){
				location.setWorld(gameMap);
			}
			for (Location location : arena.getGame().getGold()){
				location.setWorld(gameMap);
			}
			for (Location location : arena.getGame().getDiamond()){
				location.setWorld(gameMap);
			}
			for (Location location : arena.getGame().getLapis()){
				location.setWorld(gameMap);
			}
			arena.setStatus(true);
			arena.getGame().setGameStatus(GameStatus.WAIT);
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
				arena.getMap().unload();
				arena.getGame().getMap().unload();
			} catch (Exception e){
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}
}