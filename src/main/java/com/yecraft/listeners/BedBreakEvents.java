package com.yecraft.listeners;

import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import com.yecraft.bedwars.BedWars;
import com.yecraft.engine.Arena;
import com.yecraft.engine.ArenaUtilities;
import com.yecraft.engine.GameStatus;
import com.yecraft.engine.Team;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class BedBreakEvents implements Listener{

	private static final Set<Material> BEDS = EnumSet.of(
			Material.BLACK_BED,
			Material.BLUE_BED,
			Material.BROWN_BED,
			Material.CYAN_BED,
			Material.GRAY_BED,
			Material.GREEN_BED,
			Material.LIGHT_BLUE_BED,
			Material.LIGHT_GRAY_BED,
			Material.LIME_BED,
			Material.MAGENTA_BED,
			Material.ORANGE_BED,
			Material.PINK_BED,
			Material.PURPLE_BED,
			Material.RED_BED,
			Material.WHITE_BED,
			Material.YELLOW_BED
	);

	@EventHandler
	public void breakBed(BlockBreakEvent e) {
		Block block = e.getBlock();
		Player player = e.getPlayer();
		Arena arena = ArenaUtilities.getPlayerArena(player);
		if (BEDS.contains(block.getType())){
			if (arena == null) return;
			if (!(ArenaUtilities.getGameStatusByArena(arena).equals(GameStatus.ACTIVE))) return;
			Team team = ArenaUtilities.getPlayerTeam(arena, player);
			if (team != null){
				if (!team.getBed().equals(block.getType())) return;
				player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("Ви не можете зламати своє ліжко"));
				e.setCancelled(true);
				return;
			}
			Team team1 = ArenaUtilities.getTeamByMaterial(arena, block.getType());
			if (team1 != null){
				team1.setRespawnable(false);
				e.setDropItems(false);
				team1.getPlayers().stream()
						.map(Bukkit::getPlayer)
						.filter(Objects::nonNull)
						.forEach(player1 -> {
							player1.sendTitle("Ваше ліжко знищене!", "Зламав гравець " + player.getDisplayName(), 10, 20, 10);
						});
			}
		}
		if (arena != null){
			if (!(ArenaUtilities.getGameStatusByArena(arena).equals(GameStatus.ACTIVE))) return;
			if (ArenaUtilities.ifBlockCanBreak(arena, block)){
				arena.getGame().getBreakingBlocks().remove(block.getLocation());
			} else {
				e.setCancelled(true);
			}
		}
	}
}