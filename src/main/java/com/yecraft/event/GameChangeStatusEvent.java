package com.yecraft.event;

import com.yecraft.engine.Arena;
import com.yecraft.engine.Game;
import com.yecraft.engine.GameStatus;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameChangeStatusEvent extends Event{
	private static final HandlerList HANDLERS_LIST = new HandlerList();
	private Arena arena;
	private Game game;
	private GameStatus gameStatus;
	
	public GameChangeStatusEvent(Arena arena){
		this.arena = arena;
		this.game = arena.getGame();
		this.gameStatus = arena.getGame().getGameStatus();
	}

	@Override
	public HandlerList getHandlers() {
		return HANDLERS_LIST;
	}

	public static HandlerList getHandlerList() {
		return HANDLERS_LIST;
	}
}