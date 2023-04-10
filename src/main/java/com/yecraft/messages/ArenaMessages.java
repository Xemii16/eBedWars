package com.yecraft.messages;

import com.yecraft.engine.Arena;
import com.yecraft.messages.EMessage;

import net.md_5.bungee.api.ChatColor;

import java.util.Objects;

public class ArenaMessages {
	public static String get(EMessage emsg,Arena arena){
        if (Objects.requireNonNull(emsg) == EMessage.CREATE) {
            return ChatColor.GREEN + "Арена " + arena.getName() + " створена успішно!";
        }
        return "нема строки :(";
    }
}