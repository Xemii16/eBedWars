package com.yecraft.items;

import com.yecraft.inventory.SearchInventory;
import com.yecraft.inventory.TeamInventory;
import mc.obliviate.inventory.Icon;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class SpectatorItems {

    public static final String LEAVE_TITLE = "Вийти";

    private final Icon searcher;
    private final Icon leave;
    public SpectatorItems() {
        this.searcher = new Icon(Material.PLAYER_HEAD)
                .setName("Знайти гравця")
                .onClick(inventoryClickEvent -> new SearchInventory((Player) inventoryClickEvent.getWhoClicked()).open());
        this.leave = new Icon(Material.REDSTONE_TORCH)
                .setName(LEAVE_TITLE);
    }

    public void setItemsInPlayerInventory(Player player){
        player.getInventory().setItem(0, this.searcher.getItem());
        player.getInventory().setItem(8, this.leave.getItem());
    }

    public Icon getSearcher() {
        return searcher;
    }

    public Icon getLeave() {
        return leave;
    }
}
