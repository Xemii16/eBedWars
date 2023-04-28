package com.yecraft.scheduler;

import com.yecraft.engine.Arena;
import com.yecraft.engine.GameStatus;
import com.yecraft.event.GameChangeStatusEvent;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class BossBarRunnable extends BukkitRunnable {

    private Arena arena;
    private int time;

    public BossBarRunnable(Arena arena, int time) {
        this.arena = arena;
        this.time = time;
    }

    @Override
    public void run() {
        if (time == 0){
            arena.getGame().setGameStatus(GameStatus.DRAW);
            Bukkit.getPluginManager().callEvent(new GameChangeStatusEvent(arena));
            return;
        }
        arena.getBossBar().setTitle(time / 60 + " хв" + time % 60 + " сек");
        arena.getBossBar().setProgress((double) time / 3600);
    }
}
