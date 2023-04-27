package com.yecraft.engine;

import java.io.Serializable;
import java.util.*;

import org.bukkit.Location;
import org.bukkit.Material;

public class Team implements Serializable {
	private String localName;
	private String displayName;
	private String color;
	private Material bed;
	private Material wool;
	private List<UUID> players;
	private Location spawn;
	private boolean isRespawnable;

	private int slot;
	
	public Team(String localName){
		this.localName = localName;
		this.color = "#ffffff";
		this.bed = Material.WHITE_BED;
		this.players = new ArrayList<>();
		this.spawn = null;
		this.isRespawnable = true;
		this.wool = Material.WHITE_WOOL;
		this.slot = 0;
		this.displayName = "ШАБЛОН";
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public int getSlot() {
		return slot;
	}

	public void setSlot(int slot) {
		this.slot = slot;
	}

	public String getLocalName() {
		return localName;
	}

	public void setLocalName(String localName) {
		this.localName = localName;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
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

	public List<UUID> getPlayers() {
		return players;
	}

	public void setPlayers(List<UUID> players) {
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