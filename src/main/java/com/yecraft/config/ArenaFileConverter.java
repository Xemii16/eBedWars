package com.yecraft.config;

import com.yecraft.bedwars.BedWars;
import com.yecraft.engine.*;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.*;
import java.util.*;

public class ArenaFileConverter {

    private File directory;

    public ArenaFileConverter() {
        this.directory = new File(BedWars.getInstance().getDataFolder() + File.separator + "arenas");
    }

    private void createDirectory(){
        if (directory.exists()) return;
        if (directory.mkdir()){
            BedWars.getInstance().getLogger().info("Папка для збереження створено!");
        } else {
            BedWars.getInstance().getLogger().warning("Папка для збереження не створено!");
        }
    }
    public void conventArenas() {
        createDirectory();
        Arena.ARENA_MAP.values()
                .forEach(arena -> {
                    try {
                        ArenaUtilities.unloadWorlds(arena);
                        arena.getMap().unload();
                        arena.getGame().getMap().unload();
                        FileOutputStream fileOutputStream = new FileOutputStream(directory + File.separator + arena.getName() + ".arena");
                        BukkitObjectOutputStream objectOutputStream = new BukkitObjectOutputStream(fileOutputStream);
                        objectOutputStream.writeObject(arena);
                        fileOutputStream.close();
                        objectOutputStream.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                        BedWars.getInstance().getLogger().warning(String.format("Арена %s не збережена", arena.getName()));
                    }
                });
    }

    public void convertFiles() {
        createDirectory();
        if (directory.list() == null) return;
        for (File file : Objects.requireNonNull(directory.listFiles())){
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                BukkitObjectInputStream bukkitObjectInputStream = new BukkitObjectInputStream(fileInputStream);
                Arena arena = (Arena) bukkitObjectInputStream.readObject();
                arena.setStatus(false);
                arena.getGame().setGameStatus(GameStatus.TUNING);
                if (!arena.getMap().getSourceWorldFolder().getName().equalsIgnoreCase("template")){
                    arena.getMap().load();
                }
                if (!arena.getGame().getMap().getSourceWorldFolder().getName().equalsIgnoreCase("template")){
                    arena.getGame().getMap().load();
                }
                ArenaUtilities.loadWorlds(arena);
                arena.setStatus(true);
                arena.getGame().setGameStatus(GameStatus.WAIT);
                Arena.ARENA_MAP.put(arena.getName(), arena);
                fileInputStream.close();
                bukkitObjectInputStream.close();
                file.delete();
            } catch (Exception e){
                e.printStackTrace();
            }

        }
    }
}
