package com.yecraft.shop;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import mc.obliviate.inventory.Gui;
import mc.obliviate.inventory.Icon;

public class PotionShop extends Gui{
	public PotionShop(Player player){
		super (player, "potion_shop", "Зілля", 6);
	}
	
	@Override
	public void onOpen(InventoryOpenEvent inventory){
		allShopIcons();
		Icon speed = new Icon((Material.POTION));
		setEffectType(speed, PotionEffectType.SPEED, 6000, 1, true, true);
		Icon health1 = new Icon(Material.POTION);
		setEffectType(health1, PotionEffectType.HEAL, 0, 1, true, true);
		Icon health2 = new Icon(Material.POTION);
		setEffectType(health2, PotionEffectType.HEAL, 0, 2, true, true);
		Icon regeneration = new Icon(Material.POTION);
		setEffectType(regeneration, PotionEffectType.REGENERATION, 300, 1, true, true);
		Icon invisible = new Icon(Material.POTION);
		setEffectType(invisible, PotionEffectType.INVISIBILITY, 1200, 1, true, true);
		addItem(29, speed);
		addItem(30, health1);
		addItem(31, health2);
		addItem(32, regeneration);
		addItem(33, invisible);
	}
	
	public void setEffectType (Icon icon, PotionEffectType type, int time, int level, boolean ambient, boolean overwrite){
		PotionMeta meta = (PotionMeta) icon.getItem().getItemMeta();
		meta.addCustomEffect(new PotionEffect(type, time, level, ambient), overwrite);
		icon.getItem().setItemMeta(meta);
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