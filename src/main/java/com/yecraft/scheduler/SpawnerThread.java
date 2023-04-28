package com.yecraft.scheduler;

import com.yecraft.bedwars.BedWars;
import com.yecraft.engine.Arena;
import com.yecraft.engine.GameStatus;
import org.bukkit.Bukkit;

public class SpawnerThread extends Thread{

    private Arena arena;
    private final DropResourcesRunnable runnable;
    private final int millisRespawn;

    private boolean isSpawn;

    public SpawnerThread(Arena arena, DropResourcesRunnable runnable, int millisRespawn, boolean isSpawn) {
        this.arena = arena;
        this.runnable = runnable;
        this.millisRespawn = millisRespawn;
        this.isSpawn = isSpawn;
    }

    @Override
    public void run(){
        while (isSpawn){
            DropResourcesRunnable newRunnable = runnable;
            if (arena.getGame().getGameStatus() != GameStatus.ACTIVE){
                newRunnable.cancel();
                isSpawn = false;
                return;
            }
            runnable.runTask(BedWars.getInstance());
            try {
                Thread.sleep(millisRespawn);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Bukkit.getLogger().warning("Spawner thread error");
            }
        }
    }

    public boolean isSpawn() {
        return isSpawn;
    }

    public void setSpawn(boolean spawn) {
        isSpawn = spawn;
    }

    public Arena getArena() {
        return arena;
    }

    public DropResourcesRunnable getRunnable() {
        return runnable;
    }

    public int getMillisRespawn() {
        return millisRespawn;
    }
}
