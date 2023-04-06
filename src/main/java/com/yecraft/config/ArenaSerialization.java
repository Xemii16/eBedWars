package com.yecraft.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.yecraft.engine.Arena;
import org.bukkit.Bukkit;

public class ArenaSerialization {
	private static File file;
	
	private static File setup(){
		file = new File(Bukkit.getWorldContainer().getParentFile() + File.separator + "arenas");
		if (!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return file;
	}

	public static void deserialize(){
		File arenasFolder = setup();
		if (arenasFolder.list().length == 0) return;
		for (File file : arenasFolder.listFiles()){
			try {
				FileInputStream fileInputStream = new FileInputStream(file);
				ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
				Arena arena = (Arena) objectInputStream.readObject();
				Arena.ARENA_MAP.put(arena.getName(), arena);
				objectInputStream.close();
			} catch (Exception e){
				e.printStackTrace();
			}
		}
	}

	public static void serialize(){
		File arenasFolder = setup();
		for (Arena arena : Arena.ARENA_MAP.values()){
			try {
				FileOutputStream fileOutputStream = new FileOutputStream(new File(arenasFolder + File.separator + arena.getName()));
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
				objectOutputStream.writeObject(arena);
				objectOutputStream.close();
			} catch (Exception e){
				e.printStackTrace();
			}
		}
	}
}