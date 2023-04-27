package com.yecraft.engine;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

import com.yecraft.world.LocalGameMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

public class Arena implements Serializable {
    public static Map<UUID, String> UUID_ARENA = new HashMap<>();
    public static Map<String, Arena> ARENA_MAP = new HashMap<>();
    private final String name;
    private Integer playersOnTeam;
    private Integer minPlayers;
    private Integer numberTeams;
    private Game game;
    private Boolean status;
    private LocalGameMap map;
    private transient BossBar bossBar;
    private List<UUID> players;
    private Map<UUID, Location> lastPlayerLocation;

    private Location spawn;


    public Arena (String name, Integer playersOnTeam, Integer minPlayers, Integer numberTeams, Game game, boolean status, LocalGameMap map, Location spawn){
        this.name = name;
        this.playersOnTeam = playersOnTeam;
        this.minPlayers = minPlayers;
        this.numberTeams = numberTeams;
        this.game = game;
        this.status = status;
        this.map = map;
        this.bossBar = Bukkit.createBossBar("Очікування гравців", BarColor.BLUE, BarStyle.SOLID);
        this.players = new ArrayList<>();
        this.lastPlayerLocation = new HashMap<UUID, Location>();
        this.spawn = spawn;
    }


    public Location getSpawn() {
        return spawn;
    }

    public void setSpawn(Location spawn) {
        this.spawn = spawn;
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

    public List<UUID> getPlayers() {
        return players;
    }

    public void setPlayers(List<UUID> players) {
        this.players = players;
    }

    public Map<UUID, Location> getLastPlayerLocation() {
        return lastPlayerLocation;
    }

    public void setLastPlayerLocation(Map<UUID, Location> lastPlayerLocation) {
        this.lastPlayerLocation = lastPlayerLocation;
    }
}