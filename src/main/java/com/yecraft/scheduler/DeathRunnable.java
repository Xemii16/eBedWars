package com.yecraft.scheduler;

import com.yecraft.engine.Arena;
import com.yecraft.engine.ArenaUtilities;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class DeathRunnable extends BukkitRunnable {

    private Arena arena;
    private Player player;
    private int time;
    private Location nextLocation;

    public DeathRunnable(Arena arena, Player player, int time, Location nextLocation) {
        this.arena = arena;
        this.player = player;
        this.time = time;
        this.nextLocation = nextLocation;
    }

    @Override
    public void run() {
        if (time == 0){
            this.cancel();
            player.teleport(nextLocation);
            return;
        }
        player.sendTitle("Відроження через " + time--, "", 1, 19, 1);
    }
}
