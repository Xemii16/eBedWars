package com.yecraft.bedwars;

import com.yecraft.commands.CreateNPC;
import com.yecraft.commands.EngineCommands;
import com.yecraft.commands.JoinCommand;
import com.yecraft.commands.OpenShop;
import com.yecraft.commands.TeamCommands;
import com.yecraft.completers.ArenaTabCompleter;
import com.yecraft.completers.TeamCompleter;
import com.yecraft.config.ArenaStorage;

import com.yecraft.engine.Arena;
import com.yecraft.listeners.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import dev.sergiferry.playernpc.api.NPCLib;
import mc.obliviate.inventory.InventoryAPI;

import java.io.File;
import java.util.logging.Logger;


public class BedWars extends JavaPlugin{

	private static BedWars bedWars;
	private static Logger logger;

	public BedWars(){
		bedWars = this;
		logger = Logger.getLogger(BedWars.class.getName());
	}

	@Override
	public void onEnable(){
		if (!getDataFolder().exists()){
			getDataFolder().mkdir();
		}
		getCommand("arena").setExecutor(new EngineCommands());
		getCommand("team").setExecutor(new TeamCommands());
		getCommand("join").setExecutor(new JoinCommand());
		getCommand("open").setExecutor(new OpenShop());
		getCommand("create").setExecutor(new CreateNPC());
		getCommand("arena").setTabCompleter(new ArenaTabCompleter());
		getCommand("team").setTabCompleter(new TeamCompleter());
		InventoryAPI invAPI = new InventoryAPI(this);
		invAPI.init();

		Bukkit.getPluginManager().registerEvents(new BedBreakEvents(), this);
		Bukkit.getPluginManager().registerEvents(new CommandSendEvents(), this);
		Bukkit.getPluginManager().registerEvents(new CraftEvents(), this);
		Bukkit.getPluginManager().registerEvents(new DamageEvents(), this);
		Bukkit.getPluginManager().registerEvents(new GameStatusEvents(), this);
		Bukkit.getPluginManager().registerEvents(new PlayerInteractEvents(), this);
		Bukkit.getPluginManager().registerEvents(new PortalEvents(), this);
		Bukkit.getPluginManager().registerEvents(new ServerLeaveEvents(), this);

		NPCLib.getInstance().registerPlugin(this);

		if (!(new File(getDataFolder().getParentFile() + "/arenas").exists())){
			new File(getDataFolder().getParentFile() + "/arenas").mkdir();
		}
		ArenaStorage.deserialize();

		for (Arena arena : Arena.ARENA_MAP.values()){
			if (arena.getMap().getWorld() == null){
				arena.getMap().load();
			}
			if(arena.getGame().getMap().getWorld() == null){
				arena.getGame().getMap().load();
			}
		}
	}

	@Override
	public void onDisable(){
		if (!(new File(getDataFolder().getParentFile() + "/arenas").exists())){
			new File(getDataFolder().getParentFile() + "/arenas").mkdir();
		}

		for (Arena arena : Arena.ARENA_MAP.values()){
			if (arena.getMap().getWorld() != null){
				arena.getMap().unload();
			}
			if(arena.getGame().getMap().getWorld() != null){
				arena.getGame().getMap().unload();
			}
		}
		ArenaStorage.serialize();
	}

	public static BedWars getInstance(){
		return bedWars;
	}
}