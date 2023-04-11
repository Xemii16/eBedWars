package com.yecraft.completers;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.yecraft.engine.Arena;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import net.md_5.bungee.api.ChatColor;

public class TeamCompleter implements TabCompleter{

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		if (args.length == 1){
			return List.of(
			"create",
			"set",
			"delete"
			);
		}
		if (args.length == 2){
			if (args[0].equalsIgnoreCase("create")){
				return Arena.ARENA_MAP.keySet().stream().collect(Collectors.toList());
			}
			if (args[0].equalsIgnoreCase("set")){
				return Arena.ARENA_MAP.keySet().stream().collect(Collectors.toList());
			}
			if (args[0].equalsIgnoreCase("delete")){
				return Arena.ARENA_MAP.keySet().stream().collect(Collectors.toList());
			}
		}
		if (args.length == 3){
			if (args[0].equalsIgnoreCase("set")){
				Arena arena = Arena.ARENA_MAP.get(args[1]);
				return arena.getGame().getTeams().keySet().stream().collect(Collectors.toList());
			}
		}
		if (args.length == 4){
			if (args[0].equalsIgnoreCase("set")){
				return List.of(
				"wool",
				"spawn",
				"material",
				"color"
				);
			}
		}
		if (args.length == 5){
			if (args[0].equalsIgnoreCase("set")){
				if (args[3].equalsIgnoreCase("material")){
					return List.of(
				"white",
				"orange",
				"light_blue",
				"magenta",
				"yellow",
				"lime",
				"pink",
				"gray",
				"light_gray",
				"cyan",
				"purple",
				"blue",
				"brown",
				"green",
				"red",
				"black"
				);
				}
				if (args[3].equalsIgnoreCase("color")){
					return Arrays.asList(ChatColor.values().toString());
				}
			}
		}
		return null;
	}
	
}