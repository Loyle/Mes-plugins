package com.gmail.LoyleCraft.CutTheSaplings;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.LoyleCraft.CutTheSaplings.Commands.MyCommandExecutor;
import com.gmail.LoyleCraft.CutTheSaplings.Game.Game;
import com.gmail.LoyleCraft.CutTheSaplings.Listener.PlayerListener;

public class CutTheSaplings extends JavaPlugin {
	
	@SuppressWarnings("unused")
	private static Plugin plugin;
	public Game game;
	public static CutTheSaplings instance = null;
	private String path = "CutTheSaplings.";
	
	public void onEnable() {	
		plugin = this;
		
		getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
		
		//On définit l'executor pour les commandes commencent par cts
		getCommand("cts").setExecutor(new MyCommandExecutor(this));
		
		// Initialisation de la class Game (qui gère le jeux)
		this.game = new Game(this);
		
		//Si la map est définie, on l'initialise
		if(this.getConfig().isSet(path+"Map.Corner1") && this.getConfig().isSet(path+"Map.Corner2")) {
			this.game.GameManager.iniMap();
		}
		
		//On prepare la config
		this.saveDefaultConfig();
		this.saveConfig();
	}
	    
	public void onDisable() {
		System.out.println("[CutTheLine] ShutDown (End game ?)");
	}
	
	// Gestionnaire des permission pour les commandes
	public static Boolean hasPermission(Player p, String perm) {
	    if (perm.equalsIgnoreCase("")) {
	      return Boolean.valueOf(true);
	    }
	    if (p.isOp()) {
	      return Boolean.valueOf(true);
	    }
	    if (p.hasPermission("CTS.admin")) {
	      return Boolean.valueOf(true);
	    }
	    if (p.hasPermission(perm)) {
	      return Boolean.valueOf(true);
	    }
	    return Boolean.valueOf(false);
	}
	
	public static CutTheSaplings getPlugin() {
	    return instance;
	}
}
