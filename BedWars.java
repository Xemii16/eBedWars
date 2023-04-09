package com.yecraft.bedwars;

import com.yecraft.commands.CreateNPC;
import com.yecraft.commands.EngineCommands;
import com.yecraft.commands.JoinCommand;
import com.yecraft.commands.OpenShop;
import com.yecraft.commands.TeamCommands;
import com.yecraft.completers.ArenaTabCompleter;
import com.yecraft.config.ArenaSerialization;

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
		InventoryAPI invAPI = new InventoryAPI(this);
		invAPI.init();

		Bukkit.getPluginManager().registerEvents(new BedBreakEvent(), this);
		Bukkit.getPluginManager().registerEvents(new CommandSendEvent(), this);
		Bukkit.getPluginManager().registerEvents(new CraftEvent(), this);
		Bukkit.getPluginManager().registerEvents(new DamageEvent(), this);
		Bukkit.getPluginManager().registerEvents(new GameStatusEvent(), this);
		Bukkit.getPluginManager().registerEvents(new InventoryEvent(), this);
		Bukkit.getPluginManager().registerEvents(new PlayerBlockInteractEvent(), this);
		Bukkit.getPluginManager().registerEvents(new PortalEvent(), this);
		Bukkit.getPluginManager().registerEvents(new ServerLeaveEvent(), this);

		NPCLib.getInstance().registerPlugin(this);

		if (!(new File(getDataFolder().getParentFile() + "/arenas").exists())){
			new File(getDataFolder().getParentFile() + "/arenas").mkdir();
		}
		ArenaSerialization.deserialize();
	}

	@Override
	public void onDisable(){
		if (!(new File(getDataFolder().getParentFile() + "/arenas").exists())){
			new File(getDataFolder().getParentFile() + "/arenas").mkdir();
		}
		ArenaSerialization.serialize();
	}

	public static BedWars getInstance(){
		return bedWars;
	}
}