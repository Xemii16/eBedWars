package com.yecraft.engine;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Material;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.md_5.bungee.api.ChatColor;

public class Team {
	private String name;
	private ChatColor color;
	private Material bed;
	private Material wool;
	private Set<UUID> players;
	private Location spawn;
	private boolean isRespawnable;
	
	public Team(String name){
		this.name = name;
		this.color = null;
		this.bed = null;
		this.players = new HashSet<UUID>();
		this.spawn = null;
		this.isRespawnable = true;
		this.wool = Material.WHITE_WOOL;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ChatColor getColor() {
		return color;
	}

	public void setColor(ChatColor color) {
		this.color = color;
	}

	public Material getBed() {
		return bed;
	}

	public void setBed(Material bed) {
		this.bed = bed;
	}

	public Material getWool() {
		return wool;
	}

	public void setWool(Material wool) {
		this.wool = wool;
	}

	public Set<UUID> getPlayers() {
		return players;
	}

	public void setPlayers(Set<UUID> players) {
		this.players = players;
	}

	public Location getSpawn() {
		return spawn;
	}

	public void setSpawn(Location spawn) {
		this.spawn = spawn;
	}

	public boolean isRespawnable() {
		return isRespawnable;
	}

	public void setRespawnable(boolean respawnable) {
		isRespawnable = respawnable;
	}
}