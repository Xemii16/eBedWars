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

public class BowShop extends Gui{
	public BowShop(Player player){
		super(player, "bow_shop", "Луки", 6);
	}
	
	@Override
	public void onOpen(InventoryOpenEvent inventory){
		allShopIcons();
		Icon bow1 = new Icon(Material.BOW).enchant(Enchantment.ARROW_INFINITE, 1);
		Icon bow2 = new Icon(Material.BOW).enchant(Enchantment.ARROW_INFINITE, 1).enchant(Enchantment.ARROW_DAMAGE, 2);
		Icon bow3 = new Icon(Material.BOW).enchant(Enchantment.ARROW_INFINITE, 1).enchant(Enchantment.ARROW_DAMAGE, 3).enchant(Enchantment.ARROW_KNOCKBACK, 1);
		Icon bow4 = new Icon(Material.BOW).enchant(Enchantment.ARROW_INFINITE, 1).enchant(Enchantment.ARROW_FIRE, 1).enchant(Enchantment.ARROW_DAMAGE, 4).enchant(Enchantment.ARROW_KNOCKBACK, 2);
		Icon bow5 = new Icon(Material.BOW).enchant(Enchantment.ARROW_INFINITE, 1).enchant(Enchantment.ARROW_DAMAGE, 5).enchant(Enchantment.ARROW_FIRE, 2).enchant(Enchantment.ARROW_KNOCKBACK, 3);
		Icon arrow = new Icon(Material.ARROW);
		addItem(29, bow1);
		addItem(30, bow2);
		addItem(31, bow3);
		addItem(32, bow4);
		addItem(33, bow5);
		
		addItem(40, arrow);
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