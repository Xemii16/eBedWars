package com.yecraft.commands;

import org.bukkit.entity.Player;

public interface SubCommand {
	void init(String[] args, Player player);
}