package com.yecraft.inventory;

import com.yecraft.bedwars.BedWars;
import com.yecraft.engine.Arena;
import com.yecraft.engine.ArenaUtilities;
import com.yecraft.engine.GameStatus;
import com.yecraft.event.GameChangeStatusEvent;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.boss.BarColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;

import mc.obliviate.inventory.Gui;
import mc.obliviate.inventory.Icon;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;


public class JoinInventory extends Gui{

	public static final String TEAM_CHOOSER = "Вибір команди";
	public static final String LEAVE_BUTTON = "Вийти";

	public JoinInventory (Player player){
		super (player, "join_gui", "Арени", 6);
	}

	@Override
	public void onOpen (InventoryOpenEvent event){
		AtomicInteger slot = new AtomicInteger(19);
		Arena.ARENA_MAP.values().stream()
				.filter(arena -> arena.getGame().getGameStatus() == GameStatus.WAIT)
				.limit(7)
				.forEach(arena -> {
					addItem(slot.get(), new Icon(Material.EMERALD_BLOCK)
							.setName(arena.getName())
							.onClick(e -> {
								if (arena.getPlayers().size() < arena.getNumberTeams() * arena.getPlayersOnTeam()){
									ArenaUtilities.addPlayerArena(arena, player);
									ArenaUtilities.startTimer(arena);
								} else {
									player.sendMessage("Арена вже немає місць!");
								}
							})
					);
					slot.getAndIncrement();
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

	public void startCounter(Arena arena){
		new Thread(() -> {
			final int[] time = {15};
			int delay = 15;
			final boolean[] loop = {true};
			while (loop[0]){
				new BukkitRunnable(){
					@Override
					public void run() {
						if (time[0] == 0){
							this.cancel();
							arena.getGame().setGameStatus(GameStatus.START);
							Bukkit.getPluginManager().callEvent(new GameChangeStatusEvent(arena));
							loop[0] = false;
							return;
						}
						arena.getBossBar().setColor(BarColor.YELLOW);
						arena.getBossBar().setTitle("Старт через " + time[0]);
						arena.getBossBar().setProgress((double) time[0] / delay);
						time[0]--;
					}
				}.runTask(BedWars.getInstance());
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
}