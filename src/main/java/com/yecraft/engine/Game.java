package com.yecraft.engine;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.yecraft.world.LocalGameMap;

import org.bukkit.Location;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class Game implements Serializable{
  private Map<String, Team> teams;
  private long bronzeCD;
  private long ironCD;
  private long goldCD;
  private long diamondCD;
  private long lapisCD;
  private GameStatus gameStatus;
  private LocalGameMap map;
  private List<Location> bronze;
  private List<Location> iron;
  private List<Location> gold;
  private List<Location> diamond;
  private List<Location> lapis;
  private List<Location> npc;

  public Game(Map<String, Team> teams, long bronzeCD, long ironCD, long goldCD, long diamondCD, long lapisCD, GameStatus gameStatus, LocalGameMap map, List<Location> bronze, List<Location> iron, List<Location> gold, List<Location> diamond, List<Location> lapis, List<Location> npc) {
    this.teams = teams;
    this.bronzeCD = bronzeCD;
    this.ironCD = ironCD;
    this.goldCD = goldCD;
    this.diamondCD = diamondCD;
    this.lapisCD = lapisCD;
    this.gameStatus = gameStatus;
    this.map = map;
    this.bronze = bronze;
    this.iron = iron;
    this.gold = gold;
    this.diamond = diamond;
    this.lapis = lapis;
    this.npc = npc;
  }

  public void addBronze(Location location){
    this.bronze.add(location);
  }
  public void addIron(Location location){
    this.iron.add(location);
  }
  public void addGold(Location location){
    this.gold.add(location);
  }
  public void addDiamond(Location location){
    this.diamond.add(location);
  }
  public void addLapis(Location location){
    this.lapis.add(location);
  }
  public void addTeam(String name, Team team){
    this.teams.put(name, team);
  }

  public void addNpc(Location location){
    this.npc.add(location);
  }

  public List<Location> getNpc() {
    return npc;
  }

  public void setNpc(List<Location> npc) {
    this.npc = npc;
  }

  public Map<String, Team> getTeams() {
    return teams;
  }

  public void setTeams(Map<String, Team> teams) {
    this.teams = teams;
  }

  public long getBronzeCD() {
    return bronzeCD;
  }

  public void setBronzeCD(long bronzeCD) {
    this.bronzeCD = bronzeCD;
  }

  public long getIronCD() {
    return ironCD;
  }

  public void setIronCD(long ironCD) {
    this.ironCD = ironCD;
  }

  public long getGoldCD() {
    return goldCD;
  }

  public void setGoldCD(long goldCD) {
    this.goldCD = goldCD;
  }

  public long getDiamondCD() {
    return diamondCD;
  }

  public void setDiamondCD(long diamondCD) {
    this.diamondCD = diamondCD;
  }

  public long getLapisCD() {
    return lapisCD;
  }

  public void setLapisCD(long lapisCD) {
    this.lapisCD = lapisCD;
  }

  public GameStatus getGameStatus() {
    return gameStatus;
  }

  public void setGameStatus(GameStatus gameStatus) {
    this.gameStatus = gameStatus;
  }

  public LocalGameMap getMap() {
    return map;
  }

  public void setMap(LocalGameMap map) {
    this.map = map;
  }

  public List<Location> getBronze() {
    return bronze;
  }

  public void setBronze(List<Location> bronze) {
    this.bronze = bronze;
  }

  public List<Location> getIron() {
    return iron;
  }

  public void setIron(List<Location> iron) {
    this.iron = iron;
  }

  public List<Location> getGold() {
    return gold;
  }

  public void setGold(List<Location> gold) {
    this.gold = gold;
  }

  public List<Location> getDiamond() {
    return diamond;
  }

  public void setDiamond(List<Location> diamond) {
    this.diamond = diamond;
  }

  public List<Location> getLapis() {
    return lapis;
  }

  public void setLapis(List<Location> lapis) {
    this.lapis = lapis;
  }
}