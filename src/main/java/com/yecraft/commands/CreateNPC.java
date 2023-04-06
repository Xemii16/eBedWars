package com.yecraft.commands;

import com.yecraft.bedwars.BedWars;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev.sergiferry.playernpc.api.NPC;
import dev.sergiferry.playernpc.api.NPCLib;

public class CreateNPC implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player){
			Player player = (Player) sender;
			NPC.Global npc = NPCLib.getInstance().generateGlobalNPC(BedWars.getInstance(),"trader", player.getLocation());
			npc.setSkin("ewogICJ0aW1lc3RhbXAiIDogMTY3MjQxMjIyMDM4MywKICAicHJvZmlsZUlkIiA6ICI3YzI2YTAxY2U4NjU0NDkzOTA3NzA2OGQxZTA5ZjE5MiIsCiAgInByb2ZpbGVOYW1lIiA6ICJodG93ZXI4IiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlL2Q1OTZlN2Q4OWYwNWYyMmFiYTUwODYyNmE5Y2Y2NWUxZjEzMGUyNzIxZDFjNGQ4NmVhYjc3NWE5OTA5N2U2MzIiCiAgICB9CiAgfQp9", "j3fU4ABysEdrjuop0EYFhxXM5rqn");
			npc.setCollidable(true);
			npc.setText("Торговець");
			npc.addRunPlayerCommandClickAction("/open");
		}
		return true;
	}
	
}