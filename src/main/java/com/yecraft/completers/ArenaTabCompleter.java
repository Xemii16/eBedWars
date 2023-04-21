package com.yecraft.completers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.yecraft.bedwars.BedWars;
import com.yecraft.engine.Arena;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class ArenaTabCompleter implements TabCompleter{

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		if (args.length == 1){
			return List.of(
			"create",
			"set",
			"delete",
			"get",
			"info",
			"teleport"
			);
		}
		if (args.length == 4){
			if (args[0].equalsIgnoreCase("set")){
				if (args[2].equalsIgnoreCase("location")){
					return List.of(
							"bronze",
							"iron",
							"gold",
							"diamond",
							"lapis",
							"spawn",
							"npc",
							"death"

					);
				}
				if (args[2].equalsIgnoreCase("status")){
					return List.of(
					"true",
					"false"
					);
				}
				if (args[2].equalsIgnoreCase("players_on_team") || args[2].equalsIgnoreCase("number_teams")){
					return List.of(
					"1",
					"2",
					"3",
					"4",
					"5",
					"6",
					"7",
					"8",
					"9",
					"10"
					);
				}
				if (args[2].equalsIgnoreCase("bronze_cd")
				 || args[2].equalsIgnoreCase("iron_cd")
				 || args[2].equalsIgnoreCase("gold_cd")
				 || args[2].equalsIgnoreCase("diamond_cd")
				 || args[2].equalsIgnoreCase("lapis_cd")){
					return List.of("ticks");
				}
				if (args[2].equalsIgnoreCase("source_game") || args[2].equalsIgnoreCase("source_lobby")){
					List<String> folders = new ArrayList<>();
					for (File file : BedWars.getInstance().getDataFolder().listFiles()){
						folders.add(file.getName());
					}
					return folders;
				}
			}
		}
		
		if (args.length == 3){
			if(args[0].equalsIgnoreCase("teleport")){
				return List.of(
				"game",
				"lobby",
				"hub"
				);
			}
			if (args[0].equalsIgnoreCase("set")){
				return List.of(
				"players_on_team",
				"bronze_cd",
				"iron_cd",
				"gold_cd",
				"diamond_cd",
				"lapis_cd",
				"source_game",
				"source_lobby",
				"number_teams",
				"status",
				"location",
				"min_players"
				);
			}
		}
		
		if (args.length == 2){
			if (args[0].equalsIgnoreCase("create")){
				return List.of("<arena>");
			}
			if (args[0].equalsIgnoreCase("set")){
				Set<String> arena = Arena.ARENA_MAP.keySet();
				return arena.stream().collect(Collectors.toList());
			}
			if (args[0].equalsIgnoreCase("delete")){
				Set<String> arena = Arena.ARENA_MAP.keySet();
				return arena.stream().collect(Collectors.toList());
			}
			if (args[0].equalsIgnoreCase("get")){
				Set<String> arena = Arena.ARENA_MAP.keySet();
				return arena.stream().collect(Collectors.toList());
			}
			if (args[0].equalsIgnoreCase("info")){
				Set<String> arena = Arena.ARENA_MAP.keySet();
				return arena.stream().collect(Collectors.toList());
			}
			if (args[0].equalsIgnoreCase("teleport")){
				Set<String> arena = Arena.ARENA_MAP.keySet();
				return arena.stream().collect(Collectors.toList());
			}
		}
		return null;
	}
	
}