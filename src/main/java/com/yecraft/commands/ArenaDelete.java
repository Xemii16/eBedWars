package com.yecraft.commands;

import com.yecraft.engine.Arena;

import org.bukkit.entity.Player;

public class ArenaDelete implements SubCommand{

	@Override
	public void init(String[] args, Player player) {
		if (!(args[0].equalsIgnoreCase("delete"))) return;
		if (Arena.ARENA_MAP.containsKey(args[1])){
			Arena.ARENA_MAP.remove(args[1]);
			player.sendMessage("Видалена арена " + args[1]);
		} else {
			player.sendMessage("Такої арени не існує");
		}
	}
	
}