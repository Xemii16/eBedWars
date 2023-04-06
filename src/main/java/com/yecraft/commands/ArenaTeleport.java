package com.yecraft.commands;

import com.yecraft.engine.Arena;

import org.bukkit.entity.Player;

public class ArenaTeleport implements SubCommand{

	@Override
	public void init(String[] args, Player player) {
		if (args[0].equalsIgnoreCase("teleport")){
			Arena arena;
			if (Arena.ARENA_MAP.containsKey(args[1])){
				arena = Arena.ARENA_MAP.get(args[1]);
			} else {
				player.sendMessage("Арени " + args[1] + " не існує :(");
				return;
			}
			if (args[2].equalsIgnoreCase("lobby")){
				player.teleport(arena.getMap().getWorld().getSpawnLocation());
				player.sendMessage(String.format("Вас телепортовано до світу %s", arena.getMap().getWorld().getName()));
			}
			if (args[2].equalsIgnoreCase("game")){
				player.teleport(arena.getGame().getMap().getWorld().getSpawnLocation());
				player.sendMessage(String.format("Вас телепортовано до світу %s", arena.getGame().getMap().getWorld().getName()));
			}
		}
	}
}