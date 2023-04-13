package com.yecraft.inventory;

import com.yecraft.bedwars.BedWars;
import com.yecraft.engine.Arena;
import com.yecraft.engine.GameStatus;
import com.yecraft.event.GameChangeStatusEvent;

import com.yecraft.scheduler.CountRunnable;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;

import mc.obliviate.inventory.Gui;
import mc.obliviate.inventory.Icon;
import org.bukkit.inventory.meta.ItemMeta;

public class JoinInventory extends Gui{

	public static final String TEAM_CHOOSER = "Вибір команди";
	public static final String LEAVE_BUTTON = "Вийти";

	public JoinInventory (Player player){
		super (player, "join_gui", "Арени", 6);
	}

	@Override
	public void onOpen (InventoryOpenEvent event){
		Arena.ARENA_MAP.values().stream()
				.filter(Arena::getStatus)
				.limit(7)
				.forEach(arena -> {
					Icon icon = new Icon(Material.EMERALD_BLOCK)
							.appendLore("Гравців: " + arena.getPlayers().size())
							.setAmount(arena.getPlayers().size())
							.onClick(e -> {
								if (arena.getPlayers().size() != arena.getMaxPlayers()){
									player.teleport(arena.getSpawn());
									addLobbyItems(player);
									startChecker(arena);
								} else {
									player.sendMessage("Арена вже немає місць!");
								}

							});
					addItem(2 ,icon);
				});
	}

	public void addLobbyItems(Player player){
		ItemStack teamChooser = new ItemStack(Material.RED_BED);
		ItemMeta teamChooserMeta = teamChooser.getItemMeta();
		teamChooserMeta.setDisplayName(TEAM_CHOOSER);
		teamChooser.setItemMeta(teamChooserMeta);
		ItemStack leaveButton = new ItemStack(Material.REDSTONE_TORCH);
		ItemMeta leaveButtonMeta = leaveButton.getItemMeta();
		leaveButtonMeta.setDisplayName(LEAVE_BUTTON);
		leaveButton.setItemMeta(leaveButtonMeta);
		player.getInventory().clear();
		player.getInventory().setItem(0, teamChooser);
		player.getInventory().setItem(8, leaveButton);
	}

	public void startChecker(Arena arena){
		if (arena.getPlayers().size() >= (arena.getMaxPlayers() - 1)){
			new Thread(() -> {
				new CountRunnable(arena.getPlayers(), 10, "").runTaskTimer(BedWars.getInstance(), 0, 20);
				arena.getGame().setGameStatus(GameStatus.START);
				Bukkit.getPluginManager().callEvent(new GameChangeStatusEvent(arena));
			}).start();
			return;
		}
		if (arena.getPlayers().size() >= (arena.getMaxPlayers() - 3)){
			new Thread(() -> {
				new CountRunnable(arena.getPlayers(), 15, "").runTaskTimer(BedWars.getInstance(), 0, 20);
				arena.getGame().setGameStatus(GameStatus.START);
				Bukkit.getPluginManager().callEvent(new GameChangeStatusEvent(arena));
			}).start();
			return;
		}
		if (arena.getPlayers().size() >= (arena.getMaxPlayers() - 6)){
			new Thread(() -> {
				new CountRunnable(arena.getPlayers(), 30, "").runTaskTimer(BedWars.getInstance(), 0, 20);
				arena.getGame().setGameStatus(GameStatus.START);
				Bukkit.getPluginManager().callEvent(new GameChangeStatusEvent(arena));
			}).start();
		}
	}
}