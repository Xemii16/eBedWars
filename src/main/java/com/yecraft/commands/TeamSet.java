package com.yecraft.commands;

import com.yecraft.engine.Arena;
import com.yecraft.engine.Team;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class TeamSet implements SubCommand{

	@Override
	public void init(String[] args, Player player) {
		if (!(args.length == 5)) return;
		Arena arena = null;
		Team team = null;
		if (Arena.ARENA_MAP.containsKey(args[1])){
			arena = Arena.ARENA_MAP.get(args[1]);
			if (arena.getGame().getTeams().containsKey(args[2])){
				team = arena.getGame().getTeams().get(args[2]);
			} else return;
		} else return;
		
		if (args[0].equalsIgnoreCase("set")){
			if (args[3].equalsIgnoreCase("wool")){
				team.setWool(Material.getMaterial(args[4]));
				player.sendMessage("Успішно встановлено шерсть команди");
			}
			if(args[3].equalsIgnoreCase("spawn")){
                if (args[4].equals("this")) {
                    team.setSpawn(player.getLocation());
                    player.sendMessage("Встановлено локацію команди" + team.getName() + " для арени " + arena.getName());
                }
                player.sendMessage("Бот, тр хрень написав");
            }
			if (args[3].equalsIgnoreCase("material")){
				switch (args[4]){
					case "white":
						team.setBed(Material.WHITE_BED);
						player.sendMessage("Для арени " + arena.getName() +  "(команда " + team.getName() + "), встановлено колір ліжка: Білий");
						break;
					case "orange":
						team.setBed(Material.ORANGE_BED);
						player.sendMessage("Для арени " + arena.getName() +  "(команда " + team.getName() + "), встановлено колір ліжка: Оранжевий");
						break;
					case "light_blue":
						team.setBed(Material.LIGHT_BLUE_BED);
						player.sendMessage("Для арени " + arena.getName() +  "(команда " + team.getName() + "), встановлено колір ліжка: СВІТЛО СИНІЙ");
						break;
					case "magenta":
						team.setBed(Material.MAGENTA_BED);
						player.sendMessage("Для арени " + arena.getName() +  "(команда " + team.getName() + "), встановлено колір ліжка: МАЛИНОВИЙ");
						break;
						case "yellow":
						team.setBed(Material.YELLOW_BED);
						player.sendMessage("Для арени " + arena.getName() +  "(команда " + team.getName() + "), встановлено колір ліжка: ЖОВТИЙ");
						break;
					case "lime":
						team.setBed(Material.LIME_BED);
						player.sendMessage("Для арени " + arena.getName() +  "(команда " + team.getName() + "), встановлено колір ліжка: ЛАЙМОВИЙ");
						break;
					case "pink":
						team.setBed(Material.PINK_BED);
						player.sendMessage("Для арени " + arena.getName() +  "(команда " + team.getName() + "), встановлено колір ліжка: РОЖЕВИЙ");
						break;
					case "gray":
						team.setBed(Material.GRAY_BED);
						player.sendMessage("Для арени " + arena.getName() +  "(команда " + team.getName() + "), встановлено колір ліжка: СІРИЙ");
						break;
					case "light_gray":
						team.setBed(Material.LIGHT_GRAY_BED);
						player.sendMessage("Для арени " + arena.getName() +  "(команда " + team.getName() + "), встановлено колір ліжка: СВІТЛО-СІРИЙ");
						break;
					case "cyan":
						team.setBed(Material.CYAN_BED);
						player.sendMessage("Для арени " + arena.getName() +  "(команда " + team.getName() + "), встановлено колір ліжка: БЛАКИТНИЙ");
						break;
					case "purple":
						team.setBed(Material.PURPLE_BED);
						player.sendMessage("Для арени " + arena.getName() +  "(команда " + team.getName() + "), встановлено колір ліжка: ФІОЛЕТОВИЙ");
						break;
					case "blue":
						team.setBed(Material.BLUE_BED);
						player.sendMessage("Для арени " + arena.getName() +  "(команда " + team.getName() + "), встановлено колір ліжка: СИНІЙ");
						break;
					case "brown":
						team.setBed(Material.BROWN_BED);
						player.sendMessage("Для арени " + arena.getName() +  "(команда " + team.getName() + "), встановлено колір ліжка: КОРИЧНЕВИЙ");
						break;
					case "green":
						team.setBed(Material.GREEN_BED);
						player.sendMessage("Для арени " + arena.getName() +  "(команда " + team.getName() + "), встановлено колір ліжка: ЗЕЛЕНИЙ");
						break;
					case "red":
						team.setBed(Material.RED_BED);
						player.sendMessage("Для арени " + arena.getName() +  "(команда " + team.getName() + "), встановлено колір ліжка: ЧЕРВОНИЙ");
						break;
					case "black":
						team.setBed(Material.BLACK_BED);
						player.sendMessage("Для арени " + arena.getName() +  "(команда " + team.getName() + "), встановлено колір ліжка: ЧОРНИЙ");
						break;
				}
			}
			if (args[3].equalsIgnoreCase("color")){
				String color = args[4];
				try {
					team.setColor(color);
					player.sendMessage("Встановлено колір команди " + team.getName() + ": " + ChatColor.of(team.getColor()) + "ТАКИЙ");
				} catch (Exception e){
					e.printStackTrace();
					player.sendMessage("Якусь ти хрень написав");
				}
			}
		}
	}
}