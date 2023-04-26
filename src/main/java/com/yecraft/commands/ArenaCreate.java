package com.yecraft.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

import com.yecraft.engine.Arena;
import com.yecraft.engine.Game;
import com.yecraft.engine.Team;

import com.yecraft.world.LocalGameMap;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class ArenaCreate implements SubCommand{

	@Override
	public void init(String[] args, Player player) {
		if (!EngineCommands.COMMANDS.contains(this)){
			EngineCommands.COMMANDS.add(this);
		}
		if (args[0].equalsIgnoreCase("create") & args.length == 2){
			String name = args[1];
			if (!Arena.ARENA_MAP.containsKey(name)){
				Game game = new Game(new HashMap<String, Team>(), 20L, 20L, 20L, 20L, 20L, null, new LocalGameMap("template", false), new ArrayList<Location>(), new ArrayList<Location>(), new ArrayList<Location>(), new ArrayList<Location>(), new ArrayList<Location>(), new ArrayList<Location>(), null);
				Arena arena = new Arena(name, 0, 0, 0, game, false, new LocalGameMap("template",false), null);
				Arena.ARENA_MAP.put(name, arena);
				player.sendMessage("Арена " + name + " успішно створена");
			} else {
				player.sendMessage("Ім'я " + name + " зайнято :(");
			}
		}
	}
}