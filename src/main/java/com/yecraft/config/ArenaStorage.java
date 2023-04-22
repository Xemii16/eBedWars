package com.yecraft.config;

import java.io.*;

import com.yecraft.bedwars.BedWars;
import com.yecraft.engine.Arena;
import com.yecraft.engine.GameStatus;
import com.yecraft.engine.Team;
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
				if (!arena.getMap().getSourceWorldFolder().getName().equalsIgnoreCase("template")){
					arena.getMap().load();
				}
				if (!arena.getGame().getMap().getSourceWorldFolder().getName().equalsIgnoreCase("template")){
					arena.getGame().getMap().load();
				}
				Arena.ARENA_MAP.put(arena.getName(), arena);
				objectInputStream.close();
			} catch (Exception e){
				e.printStackTrace();
				return false;
			}
		}

		for (Arena arena : Arena.ARENA_MAP.values()){
			if (arena.getMap().getWorld() != null){
				if (arena.getSpawn() != null){
					World lobbyMap = arena.getMap().getWorld();
					arena.getSpawn().setWorld(lobbyMap);
				}
			}
			if (arena.getGame().getMap().getWorld() != null){
				World gameMap = arena.getMap().getWorld();
				if (arena.getGame().getDeathSpawn() != null){
					arena.getGame().getDeathSpawn().setWorld(gameMap);
				}
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
				for (Team team : arena.getGame().getTeams().values()){
					team.getSpawn().setWorld(gameMap);
				}
				arena.setStatus(true);
				arena.getGame().setGameStatus(GameStatus.WAIT);
			}
		}
		return true;
	}

	public static boolean serialize(){
		File arenasFolder = file = new File(BedWars.getInstance().getDataFolder().getParentFile() + "/arenas");
		for (Arena arena : Arena.ARENA_MAP.values()){
			try {
				if (arena.getSpawn() != null){
					arena.getSpawn().setWorld(null);
				}
				if (arena.getGame().getDeathSpawn() != null){
					arena.getGame().getDeathSpawn().setWorld(null);
				}
				for (Location location : arena.getGame().getNpc()){
					location.setWorld(null);
				}
				for (Location location : arena.getGame().getBronze()){
					location.setWorld(null);
				}
				for (Location location : arena.getGame().getIron()){
					location.setWorld(null);
				}
				for (Location location : arena.getGame().getGold()){
					location.setWorld(null);
				}
				for (Location location : arena.getGame().getDiamond()){
					location.setWorld(null);
				}
				for (Location location : arena.getGame().getLapis()){
					location.setWorld(null);
				}
				for (Team team : arena.getGame().getTeams().values()){
					if (team.getSpawn() != null){
						team.getSpawn().setWorld(null);
					}
				}
				if (arena.getGame().getMap().getWorld() != null){
					arena.getGame().getMap().unload();
				}
				if (arena.getMap().getWorld() != null){
					arena.getMap().unload();
				}
				FileOutputStream fileOutputStream = new FileOutputStream(arenasFolder + File.separator + arena.getName());
				BukkitObjectOutputStream objectOutputStream = new BukkitObjectOutputStream(fileOutputStream);
				objectOutputStream.writeObject(arena);
				objectOutputStream.close();
			} catch (Exception e){
				e.printStackTrace();
				return false;
			}
		}
		Bukkit.unloadWorld("temp_world", true);
		return true;
	}
}