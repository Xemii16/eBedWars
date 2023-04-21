package com.yecraft.inventory;

import com.yecraft.bedwars.BedWars;
import com.yecraft.engine.Arena;

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
        PersistentDataContainer data = event.getPlayer().getPersistentDataContainer();
        NamespacedKey arenaKey = new NamespacedKey(BedWars.getInstance(), "arena");
        NamespacedKey teamKey = new NamespacedKey(BedWars.getInstance(), "team");
        NamespacedKey bedKey = new NamespacedKey(BedWars.getInstance(), "bed");
        Arena arena = Arena.ARENA_MAP.get(data.get(arenaKey, PersistentDataType.STRING));
        arena.getGame().getTeams().values().stream()
                .forEach(team -> {
                    Icon icon = new Icon(team.getWool())
                            .onClick(e -> {
                                checkerIfPlayerInTeam(arena, player);
                                team.getPlayers().add(player.getUniqueId());
                                player.getPersistentDataContainer().set(teamKey, PersistentDataType.STRING, team.getLocalName());
                                player.setDisplayName(ChatColor.of(team.getColor()) + ChatColor.stripColor(player.getDisplayName()));
                            });
                    addItem(team.getSlot(), icon);
                });
    }

    public void checkerIfPlayerInTeam(Arena arena, Player player) {
        PersistentDataContainer data = player.getPersistentDataContainer();
        NamespacedKey teamKey = new NamespacedKey(BedWars.getInstance(), "team");
        if (!(data.has(teamKey, PersistentDataType.STRING))) return;
        arena.getGame().getTeams().get(data.get(teamKey, PersistentDataType.STRING))
                .getPlayers().remove(player.getUniqueId());
        data.remove(teamKey);
    }

}