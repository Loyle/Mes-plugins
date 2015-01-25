package com.gmail.LoyleCraft.KTP2135;

import java.io.File;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.LoyleCraft.KTP2135.Arena.Arena;
import com.gmail.LoyleCraft.KTP2135.Arena.ArenasManager;
import com.gmail.LoyleCraft.KTP2135.Arena.PlayerDataStore;
import com.gmail.LoyleCraft.KTP2135.Commands.MyCommandExecutor;
import com.gmail.LoyleCraft.KTP2135.Listener.PlayerListener;
import com.gmail.LoyleCraft.KTP2135.Signs.SignEditor;
import com.gmail.LoyleCraft.KTP2135.Signs.SignHandler;



public class KTP2135 extends JavaPlugin {
	
	public ArenasManager amanager;
	private static Plugin plugin;
	public static KTP2135 instance = null;
	public SignEditor signEditor;
	public PlayerDataStore Playerdt;
	
	public void onEnable() {	
		plugin = this;
		
		getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
		getServer().getPluginManager().registerEvents(new SignHandler(this), this);
		
		getCommand("ktp2135").setExecutor(new MyCommandExecutor(this));
		
		this.amanager = new ArenasManager(this);
		this.signEditor = new SignEditor(this);
		this.Playerdt = new PlayerDataStore();
		
	    final File arenasfolder = new File(getDataFolder() + File.separator + "arenas");
	    arenasfolder.mkdirs();
	    final KTP2135 instance = this;
	    
	    getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
	      public void run() {
	        for (String file : arenasfolder.list()) {
	          Arena arena = new Arena(instance, file.substring(0, file.length() - 4));
	          arena.getStructureManager().loadFromConfig();
	          arena.getStatusManager().enableArena();
	          KTP2135.this.amanager.registerArena(arena);
	        }
	        KTP2135.this.signEditor.loadConfiguration();
	      }
	    }, 20L);
		
		loadConfiguration();
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
	    if (p.hasPermission("KTP2135.admin")) {
	      return Boolean.valueOf(true);
	    }
	    if (p.hasPermission(perm)) {
	      return Boolean.valueOf(true);
	    }
	    return Boolean.valueOf(false);
	  }
	
	public void loadConfiguration(){
	    String pathKtp = "KTP2135.";
	    plugin.getConfig().addDefault(pathKtp+"SpawnLobby", null);
		plugin.getConfig().options().copyDefaults(true);
	    plugin.saveConfig();
	}
	public static KTP2135 getPlugin() {
	    return instance;
	}

}
