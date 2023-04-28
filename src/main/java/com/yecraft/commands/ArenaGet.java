package com.yecraft.commands;

import java.util.UUID;

import com.yecraft.engine.Arena;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ArenaGet implements SubCommand{

	@Override
	public void init(String[] args, Player player) {
		if (!(args.length == 2)) return;
		Arena arena = null;
		if (Arena.ARENA_MAP.containsKey(args[1])){
			arena = Arena.ARENA_MAP.get(args[1]);
		} else return;
		if (args[0].equalsIgnoreCase("get")){
			if (args[2].equalsIgnoreCase("players")){
				StringBuilder sb = new StringBuilder("Гравці:");
				for (UUID uuid : arena.getPlayers()){
					String name = Bukkit.getPlayer(uuid).getDisplayName();
					sb.append(name);
				}
				player.sendMessage(sb.toString());
			}
			if (args[2].equalsIgnoreCase("status")){
				player.sendMessage("Статус арени " + arena.getStatus().toString() + " " + arena.getGame().getGameStatus().toString());
			}
		}
	}
	
}