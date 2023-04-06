package com.yecraft.engine;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.yecraft.world.LocalGameMap;

import org.bukkit.Location;
import org.bukkit.boss.BossBar;

public class Arena {
	public static Map<String, Arena> ARENA_MAP = new HashMap<>();
	private final String name;
	private Integer playersOnTeam;
	private Integer maxPlayers;
	private Integer minPlayers;
	private Integer numberTeams;
	private Game game;
	private Boolean status;
	private LocalGameMap map;
	private BossBar bossBar;
	private Set<UUID> players;
	private Map<UUID, Location> lastPlayerLocation;
	
	public Arena (String name, Integer playersOnTeam, Integer numberTeams, Game game, boolean status, LocalGameMap map, BossBar bossBar, Set<UUID> players, Map<UUID, Location> lastPlayerLocation){
		this.name = name;
		this.playersOnTeam = playersOnTeam;
		this.maxPlayers = playersOnTeam * numberTeams;
		this.minPlayers = (playersOnTeam * numberTeams) - playersOnTeam;
		this.numberTeams = numberTeams;
		this.game = game;
		this.status = status;
		this.map = map;
		this.bossBar = bossBar;
		this.players = players;
		this.lastPlayerLocation = lastPlayerLocation;
	}

	public String getName() {
		return name;
	}

	public Integer getPlayersOnTeam() {
		return playersOnTeam;
	}

	public void setPlayersOnTeam(Integer playersOnTeam) {
		this.playersOnTeam = playersOnTeam;
	}

	public Integer getMaxPlayers() {
		return maxPlayers;
	}

	public void setMaxPlayers(Integer maxPlayers) {
		this.maxPlayers = maxPlayers;
	}

	public Integer getMinPlayers() {
		return minPlayers;
	}

	public void setMinPlayers(Integer minPlayers) {
		this.minPlayers = minPlayers;
	}

	public Integer getNumberTeams() {
		return numberTeams;
	}

	public void setNumberTeams(Integer numberTeams) {
		this.numberTeams = numberTeams;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public LocalGameMap getMap() {
		return map;
	}

	public void setMap(LocalGameMap map) {
		this.map = map;
	}

	public BossBar getBossBar() {
		return bossBar;
	}

	public void setBossBar(BossBar bossBar) {
		this.bossBar = bossBar;
	}

	public Set<UUID> getPlayers() {
		return players;
	}

	public void setPlayers(Set<UUID> players) {
		this.players = players;
	}

	public Map<UUID, Location> getLastPlayerLocation() {
		return lastPlayerLocation;
	}

	public void setLastPlayerLocation(Map<UUID, Location> lastPlayerLocation) {
		this.lastPlayerLocation = lastPlayerLocation;
	}
}