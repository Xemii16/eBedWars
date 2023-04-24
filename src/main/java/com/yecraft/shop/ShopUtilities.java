package com.yecraft.shop;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import lombok.Getter;
import lombok.Setter;
import mc.obliviate.inventory.Icon;
import net.md_5.bungee.api.ChatColor;

public class ShopUtilities {
	public static void expose(Icon icon, List<ShopUtilities.Price> prices){
		icon.appendLore("Ціна: ");
		for (ShopUtilities.Price price : prices){
			ChatColor color = price.getColor();
			Integer amount = price.getPrice();
			String name = price.getName();
			icon.appendLore("   " + color + amount + name);
		}
		icon.onClick(click -> {
			Player player = (Player) click.getWhoClicked();
			for (ShopUtilities.Price price : prices){
				if(!(player.getInventory().contains(new ItemStack(price.getMaterial()), price.getPrice()))) return;
				player.getInventory().remove(new ItemStack(price.getMaterial(), price.getPrice()));
				player.playSound(player.getLocation(), Sound.BLOCK_BAMBOO_SAPLING_PLACE, 1.0f, 1.0f);
			}
		});
	}

	static class Price {
		private PriceType type;
		private Material material;
		private Integer price;
		private ChatColor color;
		private String name;
		
		public Price (PriceType type, Integer price){
			this.type = type;
			switch (type){
				case BRONZE:
					this.material = Material.BRICK;
					this.name = "бронзи";
					this.color = ChatColor.of("#996633");
					break;
				case IRON:
					this.material = Material.IRON_INGOT;
					this.name = "заліза";
					this.color = ChatColor.of("#669999");
					break;
				case GOLD:
				this.material = Material.GOLD_INGOT;
				this.name = "золота";
				this.color = ChatColor.of("#ffff66");
				break;
				case DIAMOND:
					this.material = Material.DIAMOND;
					this.color = ChatColor.of("#66ffff");
					this.name = "діамантів";
					break;
				case LAPIS:
					this.material = Material.LAPIS_LAZULI;
					this.color = ChatColor.of("#0066ff");
					this.name = "лазуриту";
					break;
			}
			this.price = price;
		}

		public PriceType getType() {
			return type;
		}

		public void setType(PriceType type) {
			this.type = type;
		}

		public Material getMaterial() {
			return material;
		}

		public void setMaterial(Material material) {
			this.material = material;
		}

		public Integer getPrice() {
			return price;
		}

		public void setPrice(Integer price) {
			this.price = price;
		}

		public ChatColor getColor() {
			return color;
		}

		public void setColor(ChatColor color) {
			this.color = color;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}
	
	enum PriceType{
		BRONZE,
		IRON,
		GOLD,
		DIAMOND,
		LAPIS
	}
}