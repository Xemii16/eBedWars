package com.yecraft.commands;

import com.yecraft.engine.Arena;
import com.yecraft.engine.Team;

import org.bukkit.entity.Player;

public class TeamCreate implements SubCommand{

	@Override
	public void init(String[] args, Player player) {
		if (!EngineCommands.COMMANDS.contains(this)){
			EngineCommands.COMMANDS.add(this);
		}
		if (!(args.length == 3)) return;
		Arena arena = null;
		if (Arena.ARENA_MAP.containsKey(args[1])){
			arena = Arena.ARENA_MAP.get(args[1]);
		}
		//team create arena name
		if (args[0].equalsIgnoreCase("create")){
			if (!arena.getGame().getTeams().containsKey(args[2])){
				arena.getGame().addTeam(args[2], new Team(args[2]));
				player.sendMessage("Створено нову команду " + args[2] + " для арени " + args[1]);
			} else {
				player.sendMessage("Брат, ім'я " + args[2] + " зайняте :с");
			}
		}
	}
}