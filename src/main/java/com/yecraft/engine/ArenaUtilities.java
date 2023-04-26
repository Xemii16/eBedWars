package com.yecraft.engine;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.UUID;

import static com.yecraft.engine.Arena.UUID_ARENA;

public class ArenaUtilities {

    public static boolean ifBlockCanBreak(Arena arena, Block block){
        if (arena.getGame().getBreakingBlocks().contains(block.getLocation())){
            return true;
        }
        return false;
    }

    public static Team getTeamByMaterial(Arena arena, Material material){
        for (Team team : arena.getGame().getTeams().values()){
            if (!team.getBed().equals(material)) continue;
            return team;
        }
        return null;
    }

    public static void removePlayer(Arena arena, Player player){
        if (arena.getPlayers().contains(player.getUniqueId())){
            arena.getPlayers().remove(player.getUniqueId());
            UUID_ARENA.remove(player.getUniqueId());
        }
        arena.getGame().getTeams().values().stream()
                .filter(team -> team.getPlayers().contains(player.getUniqueId()))
                .forEach(team -> {
                    team.getPlayers().remove(player.getUniqueId());
                    if (team.getPlayers().size() == 0){
                        team.setRespawnable(false);
                    }
                });
    }

    public static void removePlayer(Arena arena, UUID player){
        if (arena.getPlayers().contains(player)){
            arena.getPlayers().remove(player);
            UUID_ARENA.remove(player);
        }
        arena.getGame().getTeams().values().stream()
                .filter(team -> team.getPlayers().contains(player))
                .forEach(team -> {
                    team.getPlayers().remove(player);
                    if (team.getPlayers().size() == 0){
                        team.setRespawnable(false);
                    }
                });
    }

    public static Arena getPlayerArena(Player player){
        if (!UUID_ARENA.containsKey(player.getUniqueId())) return null;
        return Arena.ARENA_MAP.get(UUID_ARENA.get(player.getUniqueId()));
    }
    public static Arena getPlayerArena(UUID player){
        if (!UUID_ARENA.containsKey(player)) return null;
        return Arena.ARENA_MAP.get(UUID_ARENA.get(player));
    }

    public static Team getPlayerTeam(Arena arena, Player player){
        for (Team team : arena.getGame().getTeams().values()){
            if (team.getPlayers().contains(player.getUniqueId())) {
                return team;
            }
        }
        return null;
    }

    public static Team getPlayerTeam(Arena arena, UUID player){
        for (Team team : arena.getGame().getTeams().values()){
            if (team.getPlayers().contains(player)) {
                return team;
            }
        }
        return null;
    }
}
