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

public class ToolsShop extends Gui{
	public ToolsShop(Player player){
		super (player, "tools_shop", "Додаткове", 6);
	}
	
	@Override
	public void onOpen(InventoryOpenEvent inventory){
		allShopIcons();
		
		Icon web = new Icon(Material.COBWEB);
		Icon fishingRod = new Icon(Material.FISHING_ROD);
		Icon steel = new Icon(Material.FLINT_AND_STEEL);
		Icon tnt = new Icon(Material.TNT);
		Icon pearl = new Icon(Material.ENDER_PEARL);
		Icon powder = new Icon(Material.BLACK_CONCRETE_POWDER).setName("Телепортатор додому");
		Icon blazeRod = new Icon(Material.BLAZE_ROD).setName("Платформа останньої надії");
		Icon compass = new Icon(Material.COMPASS).setName("СБУ трекер");
		Icon zombie = new Icon(Material.ZOMBIE_SPAWN_EGG).setName("Поставший з мертвих");
		Icon silverFish = new Icon(Material.SILVERFISH_SPAWN_EGG).setName("Найгірше створіння для суперника");
		
		addItem(29, web);
		addItem(30, fishingRod);
		addItem(31, steel);
		addItem(32, tnt);
		addItem(33, pearl);
		addItem(38, powder);
		addItem(39, blazeRod);
		addItem(40, compass);
		addItem(41, zombie);
		addItem(42, silverFish);
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