package com.yecraft.messages;

import com.yecraft.engine.Arena;
import com.yecraft.messages.EMessage;

import net.md_5.bungee.api.ChatColor;
public class ArenaMessages {
	public static String get(EMessage emsg,Arena arena){
		switch (emsg){
			case CREATE:
				return ChatColor.GREEN + "Арена " + arena.getName() + " створена успішно!";
			default:
				return "нема строки :(";
		}
	}
}