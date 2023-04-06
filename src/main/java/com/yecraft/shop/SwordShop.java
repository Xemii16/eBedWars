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

public class SwordShop extends Gui{
	public SwordShop(Player player){
		super (player, "sword_gui", "Мечі", 54);
	}
	
	@Override
	public void onOpen(InventoryOpenEvent inventory){
		allShopIcons();
		Icon stick = new Icon(Material.STICK).enchant(Enchantment.KNOCKBACK, 2);
		Icon swordGold1 = new Icon(Material.GOLDEN_SWORD).enchant(Enchantment.DAMAGE_ALL, 1);
		Icon swordGold2 = new Icon(Material.GOLDEN_SWORD).enchant(Enchantment.DAMAGE_ALL, 2);
		Icon swordIron1 = new Icon(Material.IRON_SWORD).enchant(Enchantment.DAMAGE_ALL, 1).enchant(Enchantment.KNOCKBACK, 1);
		Icon swordIron2 = new Icon(Material.IRON_SWORD).enchant(Enchantment.DAMAGE_ALL, 3).enchant(Enchantment.KNOCKBACK, 2);
		Icon swordDiamond = new Icon(Material.DIAMOND_SWORD).enchant(Enchantment.DAMAGE_ALL, 3).enchant(Enchantment.KNOCKBACK, 2);
		Icon swordNetherite = new Icon(Material.NETHERITE_SWORD).enchant(Enchantment.DAMAGE_ALL, 4).enchant(Enchantment.KNOCKBACK, 3);
		
		addItem(28, stick);
		addItem(29, swordGold1);
		addItem(30, swordGold2);
		addItem(31, swordIron1);
		addItem(32, swordIron2);
		addItem(33, swordDiamond);
		addItem(34, swordNetherite);
	}
	
	public void allShopIcons(){
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
		new Icon(Material.IRON_SWORD).setName("Мечі").enchant(Enchantment.ARROW_DAMAGE, 1).hideFlags(ItemFlag.HIDE_ENCHANTS).onClick(e -> {
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