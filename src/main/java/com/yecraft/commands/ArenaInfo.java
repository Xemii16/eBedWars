package com.yecraft.commands;

import java.util.LinkedList;
import java.util.List;

import com.yecraft.engine.Arena;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.HoverEvent.Action;
import net.md_5.bungee.api.chat.hover.content.Content;
import net.md_5.bungee.api.chat.hover.content.Text;

public class ArenaInfo implements SubCommand{

	@Override
	public void init(String[] args, Player player) {
		if (!(args.length == 2)) return;
		Arena arena = null;
		if (Arena.ARENA_MAP.containsKey(args[1])){
			arena = Arena.ARENA_MAP.get(args[1]);
		} else return;
		if (args[0].equalsIgnoreCase("info")){
			TextComponent name = new TextComponent(arena.getName());
			name.setBold(true);
			name.setColor(ChatColor.GREEN);
			name.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Ім'я арени не можна змінити. Нажміть щоб видалити")));
			name.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/arena delete " + arena.getName()));
			TextComponent playersOnTeam = new TextComponent(String.format("%d", arena.getPlayersOnTeam()));
			playersOnTeam.setBold(true);
			playersOnTeam.setColor(ChatColor.GREEN);
			playersOnTeam.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Натисніть щоб змінити к-сть гравців в команді")));
			playersOnTeam.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/arena set " + arena.getName() + " players_on_team "));
			if (playersOnTeam == null || playersOnTeam.equals("0")){
				playersOnTeam.setText("НЕМАЄ");
				playersOnTeam.setColor(ChatColor.RED);
			}
			TextComponent maxPlayers = new TextComponent(String.format("%d", arena.getMaxPlayers()));
			maxPlayers.setBold(true);
			maxPlayers.setColor(ChatColor.GREEN);
			maxPlayers.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Натисніть щоб змінити максимальну к-сть гравців в грі")));
			maxPlayers.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/arena set " + arena.getName() + " max_players "));
			if (maxPlayers == null || maxPlayers.equals("0")){
				maxPlayers.setText("НЕМАЄ");
				maxPlayers.setColor(ChatColor.RED);
			}
			TextComponent minPlayers = new TextComponent(String.format("%d", arena.getMinPlayers()));
			minPlayers.setBold(true);
			minPlayers.setColor(ChatColor.GREEN);
			minPlayers.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Натисніть щоб змінити максимальну к-сть гравців в грі")));
			minPlayers.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/arena set " + arena.getName() + " min_players "));
			if (minPlayers == null || minPlayers.equals(0)){
				minPlayers.setText("НЕМАЄ");
				minPlayers.setColor(ChatColor.RED);
			}
			TextComponent numberTeams = new TextComponent(String.format("%d", arena.getNumberTeams()));
			numberTeams.setBold(true);
			numberTeams.setColor(ChatColor.GREEN);
			numberTeams.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Натисніть щоб змінити к-сть команд у грі")));
			numberTeams.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/arena set " + arena.getName() + " number_teams "));
			if (numberTeams == null || numberTeams.equals(0)){
				numberTeams.setText("НЕМАЄ");
				numberTeams.setColor(ChatColor.RED);
			}
			TextComponent status = new TextComponent(String.format("%b", arena.getStatus()));
			status.setBold(true);
			status.setColor(ChatColor.GREEN);
			status.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Натисніть щоб змінити статус арени")));
			status.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/arena set " + arena.getName() + " status "));
			if (status == null){
				numberTeams.setText("НЕМАЄ");
				numberTeams.setColor(ChatColor.RED);
			}
			World al = arena.getMap().getWorld();
			String worldName;
			if (al == null){
				worldName = "НЕМАЄ";
			} else {
				worldName = al.getName();
			}
			TextComponent location = new TextComponent(String.format("Світ: %s (%d, %d, %d)", worldName, al.getSpawnLocation().getX(), al.getSpawnLocation().getY(), al.getSpawnLocation().getZ()));
			location.setBold(true);
			location.setColor(ChatColor.GREEN);
			location.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Натисніть щоб змінити локацію")));
			location.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/arena set " + arena.getName() + " location arena"));
			if (al == null){
				location.setText("НЕМАЄ!");
				location.setColor(ChatColor.RED);
			}
			TextComponent players = new TextComponent(String.format("%d", arena.getPlayers().size()));
			players.setBold(true);
			players.setColor(ChatColor.GREEN);
			players.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Натисніть щоб отримати повний список гравців")));
			players.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/arena get " + arena.getName() + " players"));
			if (arena.getPlayers().size() == 0){
				players.setColor(ChatColor.RED);
			}
			String additionalInformation = "ОТРИМАТИ ДЕТАЛЬНІШЕ";
			TextComponent game = new TextComponent(additionalInformation);
			game.setBold(true);
			game.setColor(ChatColor.AQUA);
			game.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/arena get " + arena.getName() + " game"));
			TextComponent text1 = new TextComponent("Опис арени");
			TextComponent n = new TextComponent ("\n");
			TextComponent text2 = new TextComponent("\n Назва: ");
			TextComponent text3 = new TextComponent("\n К-сть гравців в команді: ");
			TextComponent text4 = new TextComponent("\n Макс гравців ");
			TextComponent text5  = new TextComponent("\n Мін гравців ");
			TextComponent text6 = new TextComponent("\n К-сть команд ");
			TextComponent text7 = new TextComponent("\n Гра ");
			TextComponent text8 = new TextComponent("\n Статус ");
			TextComponent text9 = new TextComponent("\n Лоббі ");
			TextComponent text10 = new TextComponent("\n Гравці ");
			List<BaseComponent> message = new LinkedList<>();
			message.add(text2);
			message.add(name);
			message.add(text3);
			message.add(playersOnTeam);
			message.add(text4);
			message.add(maxPlayers);
			message.add(text5);
			message.add(minPlayers);
			message.add(text6);
			message.add(numberTeams);
			message.add(text7);
			message.add(game);
			message.add(text8);
			message.add(status);
			message.add(text9);
			message.add(location);
			message.add(text10);
			message.add(players);
			
			text1.setExtra(message);
			player.spigot().sendMessage(text1);
		}
	}
}