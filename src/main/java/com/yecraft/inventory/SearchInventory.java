package com.yecraft.inventory;

import com.yecraft.engine.Arena;
import com.yecraft.engine.ArenaUtilities;
import com.yecraft.engine.Team;
import mc.obliviate.inventory.Gui;
import mc.obliviate.inventory.Icon;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryOpenEvent;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class SearchInventory extends Gui {
    public SearchInventory(Player player) {
        super(player, "search_inventory", "Пошук гравців", 6);
    }

    @Override
    public void onOpen(InventoryOpenEvent e){
        Arena arena = ArenaUtilities.getPlayerArena(player);
        AtomicInteger slot = new AtomicInteger(19);
        if (arena != null){
            arena.getGame().getTeams().values().stream()
                    .filter(Team::isRespawnable)
                    .forEach(team -> {
                        team.getPlayers().stream()
                                .map(Bukkit::getPlayer)
                                .filter(Objects::nonNull)
                                .forEach(player1 -> {
                                    Icon icon = new Icon(Material.PLAYER_HEAD)
                                            .setName(player1.getDisplayName())
                                            .onClick(click -> e.getPlayer().teleport(player1.getLocation()));
                                    addItem(slot.get(), icon);
                                    slot.getAndIncrement();
                                });
                    });
        }
    }
}
