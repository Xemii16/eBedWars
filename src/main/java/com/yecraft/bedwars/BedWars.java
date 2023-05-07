package com.yecraft.bedwars;

import com.yecraft.commands.*;
import com.yecraft.completers.ArenaTabCompleter;
import com.yecraft.completers.TeamCompleter;
import com.yecraft.config.ArenaFileConverter;

import com.yecraft.engine.Arena;
import com.yecraft.listeners.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import dev.sergiferry.playernpc.api.NPCLib;
import mc.obliviate.inventory.InventoryAPI;

import java.io.File;
import java.util.List;
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
		EngineCommands.COMMANDS.addAll(List.of(
				new ArenaCreate(),
				new ArenaDelete(),
				new ArenaGet(),
				new ArenaSet(),
				new ArenaTeleport()
		));
		TeamCommands.COMMANDS.addAll(List.of(
				new TeamCreate(),
				new TeamDelete(),
				new TeamSet()
		));
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
		Bukkit.getPluginManager().registerEvents(new PlayerQuitEvents(), this);

		NPCLib.getInstance().registerPlugin(this);

		new ArenaFileConverter().convertFiles();
	}

	@Override
	public void onDisable(){
		new ArenaFileConverter().conventArenas();
	}
	public static BedWars getInstance(){
		return bedWars;
	}
}