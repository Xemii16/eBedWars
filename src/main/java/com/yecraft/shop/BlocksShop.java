package com.yecraft.shop;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import com.yecraft.engine.ArenaUtilities;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import mc.obliviate.inventory.Gui;
import mc.obliviate.inventory.Icon;

import com.yecraft.bedwars.BedWars;
import com.yecraft.engine.Arena;
import com.yecraft.engine.Team;

public class BlocksShop extends Gui{
	public BlocksShop(Player player){
		super(player, "blocks_shop", "Блоки", 6);
	}
	
	@Override
	public void onOpen(InventoryOpenEvent inventory){
		Arena arena = ArenaUtilities.getPlayerArena(player);
		if (arena == null) return;
		Team team = ArenaUtilities.getPlayerTeam(arena, player);
		if (team == null) return;
		allShopIcons();
		fillRow(new Icon(Material.ORANGE_STAINED_GLASS_PANE), 2);
		Icon icon1 = new Icon(team.getWool()).setAmount(3);
		Icon icon2 = new Icon(Material.SANDSTONE).setAmount(2);
		Icon icon3 = new Icon(Material.END_STONE);
		Icon icon4 = new Icon(Material.GLASS);
		Icon icon5 = new Icon(Material.SLIME_BLOCK).setAmount(2);
		Icon icon6 = new Icon(Material.DARK_OAK_FENCE_GATE).setAmount(4);
		Icon icon7 = new Icon(Material.OBSIDIAN);
		addItem(28, icon1);
		addItem(29, icon2);
		addItem(30, icon3);
		addItem(31, icon4);
		addItem(32, icon5);
		addItem(33, icon6);
		addItem(34, icon7);
	}
	
	public void allShopIcons(){
		fillRow(new Icon(Material.ORANGE_STAINED_GLASS_PANE).setName(""), 2);
		List<Icon> icons = List.of(
				new Icon(Material.SANDSTONE).setName("Блоки").onClick(e -> {
					new BlocksShop((Player) e.getWhoClicked()).open();
				}),
				new Icon(Material.NETHERITE_CHESTPLATE).setName("Броня").onClick(e -> {
					new ArmorShop((Player) e.getWhoClicked()).open();
				}),
				new Icon(Material.GOLDEN_PICKAXE).setName("Кайла").onClick(e -> {
					new PickaxeShop((Player) e.getWhoClicked()).open();
				}),
				new Icon(Material.IRON_SWORD).setName("Мечі").onClick(e -> {
					new SwordShop((Player) e.getWhoClicked()).open();
				}),
				new Icon(Material.BOW).setName("Луки").onClick(e -> {
					new BowShop((Player) e.getWhoClicked()).open();
				}),
				new Icon(Material.APPLE).setName("Їжа").onClick(e ->
				{
					new FoodShop((Player) e.getWhoClicked()).open();
				}),
				new Icon(Material.TNT).setName("Додаткові предмети").onClick(e -> {
					new ToolsShop((Player) e.getWhoClicked()).open();
				}),
				new Icon(Material.POTION).setName("Зілля").onClick(e -> {
					new PotionShop((Player) e.getWhoClicked()).open();
				}),
				new Icon(Material.BEACON).setName("Покращення").onClick(e -> {
					new UpgradeShop((Player) e.getWhoClicked()).open();
				}));
		int i = 0;
		for (Icon icon : icons){
			addItem(i, icon);
			i++;
		}
	}

}