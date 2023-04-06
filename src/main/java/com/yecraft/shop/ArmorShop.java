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

public class ArmorShop extends Gui{
	public ArmorShop(Player player){
		super (player, "armor_shop", "Броня", 54);
	}
	
	@Override
	public void onOpen(InventoryOpenEvent inventory){
		Icon chestplate1 = new Icon(Material.CHAINMAIL_CHESTPLATE).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
		ShopUtilities.expose(chestplate1, List.of(
			new ShopUtilities.Price(ShopUtilities.PriceType.BRONZE, 1),
			new ShopUtilities.Price(ShopUtilities.PriceType.IRON, 1),
			new ShopUtilities.Price(ShopUtilities.PriceType.GOLD, 1),
			new ShopUtilities.Price(ShopUtilities.PriceType.DIAMOND, 1),
			new ShopUtilities.Price(ShopUtilities.PriceType.LAPIS, 1)
		));
		Icon chestplate2 = new Icon(Material.CHAINMAIL_CHESTPLATE).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
		Icon chestplate3 = new Icon(Material.CHAINMAIL_CHESTPLATE).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
		Icon chestplate4 = new Icon(Material.CHAINMAIL_CHESTPLATE).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).enchant(Enchantment.THORNS, 2);
		Icon helmetIron = new Icon(Material.IRON_HELMET).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
		Icon chestplateIron = new Icon(Material.IRON_CHESTPLATE).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
		Icon leggingsIron = new Icon(Material.IRON_LEGGINGS).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
		Icon bootsIron = new Icon(Material.IRON_BOOTS).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
		Icon helmetDiamond = new Icon(Material.DIAMOND_HELMET);
		Icon chestplateDiamond = new Icon(Material.DIAMOND_CHESTPLATE);
		Icon leggingsDiamond = new Icon(Material.DIAMOND_LEGGINGS);
		Icon bootsDiamond = new Icon(Material.DIAMOND_HELMET);
		Icon netheriteChestplate1 = new Icon(Material.NETHERITE_CHESTPLATE);
		Icon netheriteChestplate2 = new Icon(Material.NETHERITE_CHESTPLATE).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
		Icon netheriteChestplate3 = new Icon(Material.NETHERITE_CHESTPLATE).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
		Icon netheriteChestplate4 = new Icon(Material.NETHERITE_CHESTPLATE).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).enchant(Enchantment.THORNS, 3);
		addItem(18, chestplate1);
		addItem(27, chestplate2);
		addItem(36, chestplate3);
		addItem(45, chestplate4);
		
		addItem(21, helmetIron);
		addItem(30, chestplateIron);
		addItem(39, leggingsIron);
		addItem(48, bootsIron);
		
		addItem(23, helmetDiamond);
		addItem(32, chestplateDiamond);
		addItem(41, leggingsDiamond);
		addItem(50, bootsDiamond);
		
		addItem(26, netheriteChestplate1);
		addItem(35, netheriteChestplate2);
		addItem(44, netheriteChestplate3);
		addItem(53, netheriteChestplate4);
	}
	
	public void allShopIcons(){
		fillRow(new Icon(Material.ORANGE_STAINED_GLASS_PANE).setName(""), 2);
		LinkedList<Icon> icons = (LinkedList<Icon>) List.of(
		new Icon(Material.SANDSTONE).setName("Блоки").onClick(e -> {
			new BlocksShop((Player) e.getWhoClicked()).open();
		}),
		new Icon(Material.NETHERITE_CHESTPLATE).setName("Броня").enchant(Enchantment.ARROW_DAMAGE).hideFlags(ItemFlag.HIDE_ENCHANTS).onClick(e -> {
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