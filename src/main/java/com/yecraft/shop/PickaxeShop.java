package com.yecraft.shop;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemFlag;

import mc.obliviate.inventory.Gui;
import mc.obliviate.inventory.Icon;

public class PickaxeShop extends Gui{
	public PickaxeShop(Player player){
		super(player, "pickaxe_shop", "Інструменти", 6);
	}
	
	@Override
	public void onOpen(InventoryOpenEvent inventory){
		allShopIcons();
		
		Icon shears1 = new Icon(Material.SHEARS);
		Icon shears2 = new Icon(Material.SHEARS).enchant(Enchantment.DIG_SPEED, 3);
		Icon pickaxeWooden = new Icon(Material.WOODEN_PICKAXE);
		Icon pickaxeIron = new Icon(Material.IRON_PICKAXE).enchant(Enchantment.DIG_SPEED, 1);
		Icon pickaxeDiamond = new Icon(Material.DIAMOND_PICKAXE).enchant(Enchantment.DIG_SPEED, 2);
		Icon pickaxeNetherite = new Icon(Material.NETHERITE_PICKAXE).enchant(Enchantment.DIG_SPEED, 3);
		
		addItem(29, shears1);
		addItem(30, shears2);
		addItem(31, pickaxeWooden);
		addItem(32, pickaxeIron);
		addItem(33, pickaxeDiamond);
		addItem(40, pickaxeNetherite);
	}
	
	public void allShopIcons(){
		fillRow(new Icon(Material.ORANGE_STAINED_GLASS_PANE).setName(""), 1);
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