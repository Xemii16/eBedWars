package com.yecraft.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class EngineCommands implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player){
			Player player = (Player) sender;
			ArenaCreate arenaCreate = new ArenaCreate();
			arenaCreate.init(args, player);
			ArenaSet arenaSet = new ArenaSet();
			arenaSet.init(args, player);
			ArenaDelete arenaDelete = new ArenaDelete();
			arenaDelete.init(args, player);
			ArenaGet arenaGet = new ArenaGet();
			arenaGet.init(args, player);
			ArenaInfo arenaInfo = new ArenaInfo();
			arenaInfo.init(args, player);
			ArenaTeleport arenaTeleport = new ArenaTeleport();
			arenaTeleport.init(args, player);
		}
		return true;
	}
	
}