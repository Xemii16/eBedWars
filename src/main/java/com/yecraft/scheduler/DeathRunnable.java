package com.yecraft.scheduler;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class DeathRunnable extends BukkitRunnable {

    private int time;
    private Player player;
    private Location nextLocation;
    public DeathRunnable(Player player, int time, Location nextLocation) {
        this.time = time;
        this.player = player;
        this.nextLocation = nextLocation;
    }

    @Override
    public void run() {
        if (time == 0){
            player.teleport(nextLocation);
            this.cancel();
        }
        player.sendTitle(ChatColor.RED + "Відродження через " + time, "", 1, 18, 1);
        time--;
    }

}
