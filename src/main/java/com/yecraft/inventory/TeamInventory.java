package com.yecraft.inventory;

import com.yecraft.bedwars.BedWars;
import com.yecraft.engine.Arena;

import com.yecraft.engine.ArenaUtilities;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import mc.obliviate.inventory.Gui;
import mc.obliviate.inventory.Icon;

public class TeamInventory extends Gui {

    public TeamInventory(Player player) {
        super(player, "team_inventory", "Вибір команди", 6);
    }

    @Override
    public void onOpen(InventoryOpenEvent event) {
        Arena arena = ArenaUtilities.getPlayerArena((Player) event.getPlayer());
        if (arena == null) return;
        arena.getGame().getTeams().values()
                .forEach(team -> {
                    Icon icon = new Icon(team.getWool())
                            .onClick(e -> ArenaUtilities.addPlayerToTeam(team, (Player) e.getWhoClicked()));
                    addItem(team.getSlot(), icon);
                });
    }


}