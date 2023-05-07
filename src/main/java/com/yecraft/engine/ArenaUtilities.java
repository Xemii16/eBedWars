package com.yecraft.engine;

import com.yecraft.bedwars.BedWars;
import com.yecraft.config.ArenaFileConverter;
import com.yecraft.event.GameChangeStatusEvent;
import com.yecraft.items.SpectatorItems;
import com.yecraft.scheduler.DeathRunnable;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static com.yecraft.engine.Arena.UUID_ARENA;

public class ArenaUtilities {

    public static void restoreWorld(Arena arena){
        arena.getPlayers().stream()
                .map(Bukkit::getPlayer)
                .filter(Objects::nonNull)
                .forEach(player -> {
                    player.getInventory().clear();
                    player.teleport(arena.getLastPlayerLocation().get(player.getUniqueId()));
                });
        arena.setLastPlayerLocation(new HashMap<>());
        arena.getBossBar().removeAll();
        setDefaultBossBar(arena);
        Game game = arena.getGame();
        game.getTeams().values().stream()
                        .filter(team -> !team.getPlayers().isEmpty())
                                .forEach(team -> team.setPlayers(new ArrayList<>()));
        unloadWorlds(arena);
        game.getMap().restoreFromSource();
        loadWorlds(arena);
        game.setGameStatus(GameStatus.WAIT);

    }

    public static void setDefaultBossBar(Arena arena) {
        BossBar bar = arena.getBossBar();
        bar.setColor(BarColor.BLUE);
        bar.setProgress(1.0);
        bar.setTitle("Очікування гравців...");
    }

    public static void loadWorlds(Arena arena){
        Game game = arena.getGame();
        Collection<Team> teams = game.getTeams().values();
        /* getters the world */
        World world = arena.getMap().getWorld();
        World world1 = game.getMap().getWorld();
        /* setting zero world values in arena locations */
        setWorld(world, arena.getSpawn());
        /* setting zero world values in game locations */
        game.getBronze().forEach(bronze -> setWorld(world1, bronze));
        game.getIron().forEach(iron -> setWorld(world1, iron));
        game.getGold().forEach(gold -> setWorld(world1, gold));
        game.getDiamond().forEach(diamond -> setWorld(world1, diamond));
        game.getLapis().forEach(lapis -> setWorld(world1, lapis));

        game.setBreakingBlocks(new ArrayList<>());

        game.getNpc().forEach(npc -> setWorld(world1, npc));
        setWorld(world1, game.getDeathSpawn());

        /* setting zero world values in teams locations */
        teams.forEach(team -> setWorld(world1, team.getSpawn()));
    }

    public static void unloadWorlds(Arena arena){
        Game game = arena.getGame();
        Collection<Team> teams = game.getTeams().values();
        /* setting zero world values in arena locations */
        setWorldNull(arena.getSpawn());
        /* setting zero world values in game locations */
        game.getBronze().forEach(ArenaUtilities::setWorldNull);
        game.getIron().forEach(ArenaUtilities::setWorldNull);
        game.getGold().forEach(ArenaUtilities::setWorldNull);
        game.getDiamond().forEach(ArenaUtilities::setWorldNull);
        game.getLapis().forEach(ArenaUtilities::setWorldNull);

        game.setBreakingBlocks(new ArrayList<>());

        game.getNpc().forEach(ArenaUtilities::setWorldNull);
        setWorldNull(game.getDeathSpawn());

        /* setting zero world values in teams locations */
        teams.forEach(team -> setWorldNull(team.getSpawn()));
    }
    private static void setWorldNull(Location location) {
        location.setWorld(null);
    }

    private static void setWorld(World world, Location location) {
        location.setWorld(world);
    }

    public static void startTimer(Arena arena){
        if (arena.getPlayers().size() != arena.getMinPlayers()) return;
        new BukkitRunnable(){
            int time = 15;
            BossBar bossBar = arena.getBossBar();
            @Override
            public void run() {
                if (time == 0){
                    this.cancel();
                    arena.getGame().setGameStatus(GameStatus.START);
                    Bukkit.getPluginManager().callEvent(new GameChangeStatusEvent(arena));
                    return;
                }
                bossBar.setColor(BarColor.YELLOW);
                bossBar.setTitle("Початок через " + time);
                arena.getPlayers().stream()
                        .map(Bukkit::getPlayer)
                        .filter(Objects::nonNull)
                        .forEach(player -> player.playNote(player.getLocation(), Instrument.BIT, Note.natural(1, Note.Tone.A)));
                time--;
            }
        }.runTaskTimer(BedWars.getInstance(), 0, 20);
    }
    public static void addPlayerArena(Arena arena, Player player){
        player.teleport(arena.getSpawn());
        arena.getPlayers().add(player.getUniqueId());
        arena.getPlayers().stream()
                        .map(Bukkit::getPlayer)
                                .filter(Objects::nonNull)
                                        .forEach(player1 -> {
                                            player1.sendMessage(String.format("Приєднався гравець %s (%d/%d)", player.getDisplayName(), arena.getPlayers().size(), arena.getMaxPlayers()));
                                            player1.playSound(player1.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 1, 1);
                                        });
        arena.getBossBar().addPlayer(player);
        UUID_ARENA.put(player.getUniqueId(), arena.getName());
        arena.getLastPlayerLocation().put(player.getUniqueId(), player.getLocation());
    }

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

    public static void gameStopper(Arena arena){
        Game game = arena.getGame();
        long liveTeams = game.getTeams().values().stream()
                .filter(team -> !team.getPlayers().isEmpty())
                .count();
        if (liveTeams <= 1){
            arena.getGame().setGameStatus(GameStatus.WIN);
            Bukkit.getPluginManager().callEvent(new GameChangeStatusEvent(arena));
        }
    }
    public static void respawnPlayerOrDoSpectator(Arena arena, Team team, Player player){
        if (team.isRespawnable()){
            player.setHealth(20);
            player.setFoodLevel(20);
            int time = 5;
            DeathRunnable runnable = new DeathRunnable(arena, player, time, arena.getGame().getDeathSpawn());
            runnable.runTaskTimer(BedWars.getInstance(), 0, 20);
        } else {
            gameStopper(arena);
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
