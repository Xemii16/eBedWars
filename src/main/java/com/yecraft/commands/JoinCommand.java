package com.yecraft.commands;

import com.yecraft.inventory.JoinInventory;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

public class JoinCommand implements CommandExecutor{

	@Override
	public boolean onCommand(@Nonnull CommandSender commandSender, @Nonnull  Command command, @Nonnull String s, @Nonnull String[] args) {
		if (commandSender instanceof Player){
			Player player = (Player) commandSender;
			new JoinInventory(player).open();
		}
		return true;
	}
}