package com.yecraft.engine;

import com.yecraft.bedwars.BedWars;
import com.yecraft.items.SpectatorItems;
import com.yecraft.scheduler.DeathRunnable;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import static com.yecraft.engine.Arena.UUID_ARENA;

public class ArenaUtilities {

    public static void addPlayerToFreeTeam(Arena arena, Player player){
        arena.getGame().getTeams().values().stream()
                .filter(team -> team.getPlayers().size() < arena.getPlayersOnTeam())
                .forEach(team -> {
                    if (team.getPlayers().size() < arena.getPlayersOnTeam()){
                        team.getPlayers().add(player.getUniqueId());
                    }
                });
    }

    public static boolean ifPlayerInTeam (Arena arena, Player player){
        for (Team team : arena.getGame().getTeams().values()){
            if (team.getPlayers().contains(player.getUniqueId())) return true;
        }
        return false;
    }
    public static void addPlayerToTeam(Team team, Player player){
        Arena arena = ArenaUtilities.getPlayerArena(player);
        if (arena == null) return;
        team.getPlayers().add(player.getUniqueId());
        player.sendMessage("Ви приєднані до команди " + team.getDisplayName());
    }
    public static void respawnPlayerOrDoSpectator(Arena arena, Team team, Player player){
        if (team.isRespawnable()){
            player.setHealth(20);
            player.setFoodLevel(20);
            AtomicInteger time = new AtomicInteger(5);
            new Thread(() -> {
                DeathRunnable runnable = new DeathRunnable(arena, player, time.get(), arena.getGame().getDeathSpawn());
                boolean bool = true;
                while (bool){
                    if (time.get() == 0){
                        bool = false;
                    }
                    runnable.run();
                    time.getAndDecrement();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        System.out.println("ArenaUtilities respawn player error");
                    }
                }
            });
        } else {
            player.setGameMode(GameMode.SPECTATOR);
            removePlayer(arena, player);
            new SpectatorItems().setItemsInPlayerInventory(player);
        }
    }
    public static void sendTitleForPlayersArena(List<UUID> players, String title, String subTitle, int fadeIn, int stay, int fadeOut){
        players.stream()
                .map(Bukkit::getPlayer)
                .filter(Objects::nonNull)
                .forEach(player -> player.sendTitle(title, subTitle, fadeIn, stay, fadeOut));
    }
    public static void sendMessageForPlayersArena(List<UUID> players, String message){
        players.stream()
                .map(Bukkit::getPlayer)
                .filter(Objects::nonNull)
                .forEach(player -> player.sendMessage(message));
    }
    public static boolean ifPlayerTeammateOfOtherPlayer (Player player, Player teammate){
        Arena arena = ArenaUtilities.getPlayerArena(player);
        if (arena == null) return false;
        Team team = ArenaUtilities.getPlayerTeam(arena, player);
        if (team == null) return false;
        return team.getPlayers().contains(teammate.getUniqueId());
    }

    public static boolean ifBlockCanBreak(Arena arena, Block block){
        return arena.getGame().getBreakingBlocks().contains(block.getLocation());
    }


    public static GameStatus getGameStatusByArena(Arena arena){
        return arena.getGame().getGameStatus();
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
            arena.getBossBar().removePlayer(Objects.requireNonNull(Bukkit.getPlayer(player)));
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
