package com.yecraft.scheduler;

import com.yecraft.engine.Arena;
import org.bukkit.boss.BossBar;
import org.bukkit.scheduler.BukkitRunnable;

public class TimerBossBar {

    private int time;
    private int delay;
    private String text;
    private BossBar bossBar;

    public TimerBossBar(Arena arena, String text, int time, int delay) {
        this.time = time;
        this.delay = delay;
        this.text = text;
        this.bossBar = arena.getBossBar();
    }

    public void start(){
        new Thread (() -> {
            boolean bool = true;
            double i = time;
            BukkitRunnable runnable = new BukkitRunnable(){
                @Override
                public void run() {
                    bossBar.setProgress(i / time);
                    bossBar.setTitle(text + i);
                }
            };
            while (bool){

            }
        });
    }
}
