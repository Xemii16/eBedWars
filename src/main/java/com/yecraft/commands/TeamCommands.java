package com.yecraft.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.yecraft.commands.TeamSet;

public class TeamCommands implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player){
			Player player = (Player) sender;
			if (player.isOp()){
				TeamCreate teamCreate = new TeamCreate();
				teamCreate.init(args, player);
				TeamDelete teamDelete = new TeamDelete();
				teamDelete.init(args, player);
				TeamSet teamSet = new TeamSet();
				teamSet.init(args, player);
			}
		}
		return true;
	}
	
}