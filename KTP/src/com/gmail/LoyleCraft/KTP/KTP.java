package com.gmail.LoyleCraft.KTP;

import java.io.File;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.LoyleCraft.KTP.Arena.Arena;
import com.gmail.LoyleCraft.KTP.Arena.ArenaManager;
import com.gmail.LoyleCraft.KTP.Arena.PlayerDataStore;
import com.gmail.LoyleCraft.KTP.Commands.MyCommandExecutor;
import com.gmail.LoyleCraft.KTP.Listener.PlayerListener;
import com.gmail.LoyleCraft.KTP.Signs.SignEditor;
import com.gmail.LoyleCraft.KTP.Signs.SignHandler;

public class KTP extends JavaPlugin {
	
	@SuppressWarnings("unused")
	private static Plugin plugin;
	public static KTP instance = null;
	public ArenaManager amanager;
	public SignEditor signEditor;
	public PlayerDataStore Playerdt;
	
	public void onEnable() {	
		plugin = this;
		
		getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
		getServer().getPluginManager().registerEvents(new SignHandler(this), this);
		
		this.amanager = new ArenaManager(this);
		this.signEditor = new SignEditor(this);
		this.Playerdt = new PlayerDataStore();
		
		getCommand("KTP").setExecutor(new MyCommandExecutor(this));
		
		
	    final File arenasfolder = new File(getDataFolder() + File.separator + "arenas");
	    arenasfolder.mkdirs();
	    final KTP instance = this;
	    
	    getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
	      public void run() {
	        for (String file : arenasfolder.list()) {
	          Arena arena = new Arena(instance, file.substring(0, file.length() - 4));
	          arena.getStructureManager().loadFromConfig();
	          arena.getStatusManager().enableArena();
	          KTP.this.amanager.registerArena(arena);
	        }
	        KTP.this.signEditor.loadConfiguration();
	      }
	    }, 20L);
		
		saveDefaultConfig();
	}
	    
	public void onDisable() {
	    for (Arena arena : this.amanager.getArenas()) {
		      arena.getStatusManager().disableArena();
		      arena.getStructureManager().saveToConfig();
		    }
		    this.signEditor.saveConfiguration();
		    this.signEditor = null;
		    
		    this.amanager = null;
	}
	
	public static Boolean hasPermission(Player p, String perm) {
	    if (perm.equalsIgnoreCase("")) {
	      return Boolean.valueOf(true);
	    }
	    if (p.isOp()) {
	      return Boolean.valueOf(true);
	    }
	    if (p.hasPermission("KTP.admin")) {
	      return Boolean.valueOf(true);
	    }
	    if (p.hasPermission(perm)) {
	      return Boolean.valueOf(true);
	    }
	    return Boolean.valueOf(false);
	}
	
	public static KTP getPlugin() {
	    return instance;
	}
}
