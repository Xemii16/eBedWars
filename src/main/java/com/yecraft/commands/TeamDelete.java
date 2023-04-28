package com.yecraft.commands;

import org.bukkit.entity.Player;

import com.yecraft.engine.Arena;

public class TeamDelete implements SubCommand{

	@Override
	public void init(String[] args, Player player) {
		if (!(args.length == 3)) return;
		Arena arena = null;
		if (Arena.ARENA_MAP.containsKey(args[1])){
			arena = Arena.ARENA_MAP.get(args[1]);
		}
		if (args[0].equalsIgnoreCase("delete")){
			if(arena.getGame().getTeams().containsKey(args[2])){
				arena.getGame().getTeams().remove(args[2]);
				player.sendMessage("Команда " + args[2] + "успішно видалена для арени " + args[1]);
			}
		}
	}
	
}