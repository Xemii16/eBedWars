package com.yecraft.shop;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryOpenEvent;

import mc.obliviate.inventory.Gui;
import mc.obliviate.inventory.Icon;

import com.yecraft.shop.BlocksShop;
import com.yecraft.shop.ArmorShop;
import com.yecraft.shop.BowShop;
import com.yecraft.shop.FoodShop;
import com.yecraft.shop.UpgradeShop;
import com.yecraft.shop.PickaxeShop;
import com.yecraft.shop.PotionShop;
import com.yecraft.shop.SwordShop;
import com.yecraft.shop.ToolsShop;

public class MainShop extends Gui {
	public MainShop(Player player){
		super (player, "main_shop", "Магазин", 9);
	}
	
	@Override
	public void onOpen(InventoryOpenEvent inventory){
		fillRow(new Icon(Material.ORANGE_STAINED_GLASS_PANE).setName(""), 2);
		LinkedList<Icon> icons = (LinkedList<Icon>) List.of(
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
		for (Icon icon : icons){
			int i = 0;
			addItem(i, icon);
			i++;
		}
	}
}