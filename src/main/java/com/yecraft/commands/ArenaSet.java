package com.yecraft.commands;

import java.io.File;

import com.yecraft.bedwars.BedWars;
import com.yecraft.engine.Arena;
import com.yecraft.engine.GameStatus;
import com.yecraft.world.LocalGameMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ArenaSet implements SubCommand{

	@Override
	public void init(String[] args, Player player) {
		if (args[0].equalsIgnoreCase("set")){
			if (!(args.length == 4)) return;
			Arena arena = null;
			if (Arena.ARENA_MAP.containsKey(args[1])){
				arena = Arena.ARENA_MAP.get(args[1]);
			} else {
				player.sendMessage("Арени " + args[1] + " не існує :(");
				return;
			}
			if(args[2].equalsIgnoreCase("players_on_team")){
				try {
					Integer number = Integer.parseInt(args[3]);
					arena.setPlayersOnTeam(number);
					player.sendMessage("Встановлено к-сть гравців в команді " + args[3]);
				} catch (NumberFormatException e){
					player.sendMessage("Цифру введи, клоун :)");
				}
			}
			if (args[2].equalsIgnoreCase("bronze_cd")){
				arena.getGame().setBronzeCD(Long.parseLong(args[3]));
				player.sendMessage("Встановлено інтервал спавну бронзи " + Long.parseLong(args[3])/20 + "с");
			}
			if (args[2].equalsIgnoreCase("iron_cd")){
				arena.getGame().setIronCD(Long.parseLong(args[3]));
				player.sendMessage("Встановлено інтервал спавну заліза " + Long.parseLong(args[3])/20 + "с");
			}
			if (args[2].equalsIgnoreCase("gold_cd")){
				arena.getGame().setGoldCD(Long.parseLong(args[3]));
				player.sendMessage("Встановлено інтервал спавну золота " + Long.parseLong(args[3])/20 + "с");
			}
			if (args[2].equalsIgnoreCase("diamond_cd")){
				arena.getGame().setDiamondCD(Long.parseLong(args[3]));
				player.sendMessage("Встановлено інтервал спавну діамантів " + Long.parseLong(args[3])/20 + "с");
			}
			if (args[2].equalsIgnoreCase("lapis_cd")){
				arena.getGame().setLapisCD(Long.parseLong(args[3]));
				player.sendMessage("Встановлено інтервал спавну лазуриту " + Long.parseLong(args[3])/20 + "с");
			}
			if (args[2].equalsIgnoreCase("source_game")){
				arena.getGame().setMap(new LocalGameMap(args[3], true));
				player.sendMessage("Світ приєднаний до арени " + args[1]);
			}
			if (args[2].equalsIgnoreCase("source_lobby")){
				arena.setMap(new LocalGameMap(args[3], true));
				player.sendMessage("Світ приєднаний до арени " + args[1]);
			}
			if (args[2].equalsIgnoreCase("number_teams")){
				arena.setNumberTeams(Integer.parseInt(args[3]));
				player.sendMessage("Встановлено к-сть команд " + args[3] + " для арени " + args[1]);
			}
			if (args[2].equalsIgnoreCase("status")){
				try{
					arena.setStatus(Boolean.parseBoolean(args[3]));
					arena.getGame().setGameStatus(GameStatus.WAIT);
				} catch (Exception e){
					e.printStackTrace();
				}
				switch (args[3]){
					case "true":
						player.sendMessage("Встановлено значення ТАК для арени " + args[1]);
						break;
					case "false":
						player.sendMessage("Встановлено значення НІ для арени" + args[1]);
						break;
					default:
						player.sendMessage("Значення неправильне");
						break;
				}
			}
			if (args[2].equalsIgnoreCase("location")){
				switch (args[3]){
					case "spawn":
						arena.setSpawn(player.getLocation());
						player.sendMessage("Локація спавну встановлена");
						break;
					case "bronze":
						arena.getGame().addBronze(player.getLocation());
						player.sendMessage("Локація бронзи встановлена");
						break;
					case "iron":
						arena.getGame().addIron(player.getLocation());
						player.sendMessage("Локація заліза встановлена");
						break;
					case "gold":
						arena.getGame().addGold(player.getLocation());
						player.sendMessage("Локація золота встановлена");
					break;
					case "diamond":
						arena.getGame().addDiamond(player.getLocation());
						player.sendMessage("Локація діамантів встановлена");
						break;
						case "lapis":
						arena.getGame().addLapis(player.getLocation());
						player.sendMessage("Локація лазуриту встановлена");
						break;
					default:
						player.sendMessage("Ти якусь хрень написав");
						break;
				}
			}
		}
	}
}