package com.gmail.LoyleCraft.KTN;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import com.gmail.LoyleCraft.KTN.Commands.MyCommandExecutor;
import com.gmail.LoyleCraft.KTN.Game.Game;
import com.gmail.LoyleCraft.KTN.Game.ScoreboardManager;
import com.gmail.LoyleCraft.KTN.Listener.PlayerListener;

public class KTN extends JavaPlugin {
	
	@SuppressWarnings("unused")
	private static Plugin plugin;
	public Game game;
	public ScoreboardManager scoreboard;
	public static KTN instance = null;
	@SuppressWarnings("unused")
	private String path = "KTN.";
	
	@Override
	public void onEnable() {
		plugin = this;
		
		//On prepare la config
		this.getConfig().options().copyDefaults(true);
		this.saveDefaultConfig();
		
		getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
		
		//On définit l'executor pour les commandes commencent par ktn
		getCommand("ktn").setExecutor(new MyCommandExecutor(this));
	    
		// Initialisation de la class Game (qui gère le jeu)
		this.scoreboard = new ScoreboardManager(this);
	    this.game = new Game(this);
	    this.game.GameManager.initialiseWorld();
	}
	
	@Override
	public void onDisable() {
		System.out.println("[KTN] ShutDown (End game ?)");
	}
	
	// Gestionnaire des permission pour les commandes
	public static Boolean hasPermission(Player p, String perm) {
	    if (perm.equalsIgnoreCase("")) {
	      return Boolean.valueOf(true);
	    }
	    if (p.isOp()) {
	      return Boolean.valueOf(true);
	    }
	    if (p.hasPermission("KTN.admin")) {
	      return Boolean.valueOf(true);
	    }
	    if (p.hasPermission(perm)) {
	      return Boolean.valueOf(true);
	    }
	    return Boolean.valueOf(false);
	}
	
	public static KTN getPlugin() {
	    return instance;
	}
}
