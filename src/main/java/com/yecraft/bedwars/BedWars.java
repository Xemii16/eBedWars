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


public class BedWars extends JavaPlugin{

	private static BedWars bedWars;

	public BedWars(){
		bedWars = this;
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

		ArenaSerialization.deserialize();
		ArenaSerialization.get().options().copyDefaults(true);
		ArenaSerialization.saveFile();

	}

	@Override
	public void onDisable(){
	}

	public static BedWars getInstance(){
		return bedWars;
	}
}