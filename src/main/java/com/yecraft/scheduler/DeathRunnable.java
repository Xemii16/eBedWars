package com.yecraft.scheduler;

import com.yecraft.engine.Arena;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class DeathRunnable extends BukkitRunnable {

    private Arena arena;
    private int time;
    private Player player;
    private Location nextLocation;
    public DeathRunnable(Player player, int time, Location nextLocation, Arena arena) {
        this.time = time;
        this.player = player;
        this.nextLocation = nextLocation;
        this.arena = arena;
    }

    @Override
    public void run() {
        if (time == 0){
            player.teleport(nextLocation);
            arena.getGame().getDeathTasks().remove(this);
            this.cancel();
        }
        player.sendTitle(ChatColor.RED + "Відродження через " + time, "", 1, 18, 1);
        time--;
    }

}
