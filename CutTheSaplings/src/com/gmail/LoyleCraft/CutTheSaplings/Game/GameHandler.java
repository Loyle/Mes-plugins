package com.gmail.LoyleCraft.CutTheSaplings.Game;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import com.gmail.LoyleCraft.CutTheSaplings.CutTheSaplings;

public class GameHandler {
	
	public CutTheSaplings plugin;
	private String path = "CutTheSaplings.";
	private int runtaskid;
	private int count;
	private int harvesttime;
	private int pvptime;
	private int stopCountDown = 5;
	
	public GameHandler(CutTheSaplings pl) {
		this.plugin = pl;
		this.count = this.plugin.getConfig().getInt(path + "Countdown");
	}
	
	// Countdown avant départ de la partie
	public void runGameCountdown() {
		// On précise au plugin que la partie est en cours de démarrage
		this.plugin.game.GameManager.setIsStarting(true);
	    this.runtaskid = Bukkit.getScheduler().scheduleSyncRepeatingTask(this.plugin, new Runnable() {
	    	public void run() {
	    		if (GameHandler.this.count <= -1) {
	    			// Si le countdown est arrivé à 0 (enfin à -1) on stop le countdown, et on démarre la partie
	    			GameHandler.this.stopGameCountdown();
	    	        GameHandler.this.startGame();
	    	        GameHandler.this.count = GameHandler.this.plugin.getConfig().getInt(path + "Countdown");
	    		}
	    		else {
	    			// Pour chaque joueurs, on affiche toutes les 60/30/10/5/4/3/2/1 secondes, les messages d'informations + son d'xp
	    			for (Player player : GameHandler.this.plugin.game.PlayersManager.getPlayers()) {
	    				if(GameHandler.this.count == 60 || GameHandler.this.count == 30 || GameHandler.this.count == 10 || GameHandler.this.count <= 5) {
	    					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title "+player.getName()+" times 5 20 5");
	    					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title "+player.getName()+" subtitle {\"text\":\"Démarrage dans "+count+" secondes !\",\"color\":\"gold\"}");
	    					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title "+player.getName()+" title {\"text\":\"Préparez-vous !\",\"color\":\"red\"}");
			    			player.playSound(player.getLocation(), Sound.BLOCK_DISPENSER_DISPENSE, 20, 1);
	    				}
	    				// On affiche dans la barre d'xp, le temps restant avant le début
		    			player.setLevel(GameHandler.this.count);
		    		}
		    		GameHandler.this.count--;
	    		}
	    	}
	    },0L,20L);
	}
	// Pour forcer l'arrêt du countdown si plus assez de joueurs
	public void stopGameCountdown() {
	    Bukkit.getScheduler().cancelTask(this.runtaskid);
	    this.plugin.game.GameManager.setIsStarting(false);
	}
	public void startGame() {
		this.plugin.game.GameManager.setIsStart(true);
		this.harvesttime = this.plugin.getConfig().getInt(path+"HarvestTime");
		this.pvptime = this.plugin.getConfig().getInt(path+"PVPTime");	
		
		for (Player player : this.plugin.game.PlayersManager.getPlayers()) {
			player.setFoodLevel(20000);
			player.setHealth(20.0);
			player.getInventory().clear();
			player.setGameMode(GameMode.SURVIVAL);
			for (PotionEffect effect : player.getActivePotionEffects()) {
		        player.removePotionEffect(effect.getType());
			}
			
			player.sendMessage(ChatColor.GOLD + "C'est partie ! Vous avez "+this.harvesttime+" secondes pour ramasser le plus possible de pousses d'arbres !");
			Double X = this.plugin.getConfig().getDouble(path+"Map.SpawnPoint.X");
			Double Y = this.plugin.getConfig().getDouble(path+"Map.SpawnPoint.Y");
			Double Z = this.plugin.getConfig().getDouble(path+"Map.SpawnPoint.Z");
			Float Yaw = (float) this.plugin.getConfig().getDouble(path+"Map.SpawnPoint.YAW");
			Float Pitch = (float) this.plugin.getConfig().getDouble(path+"Map.SpawnPoint.PITCH");
			World World = Bukkit.getServer().getWorld(this.plugin.getConfig().getString(path+"Map.SpawnPoint.World"));
			
			Location l = new Location(World, X, Y, Z, Yaw, Pitch);
			player.teleport(l);
		}
		
		
		this.runtaskid = Bukkit.getScheduler().scheduleSyncRepeatingTask(this.plugin, new Runnable() {
	    	public void run() {
	    			
	    		if(GameHandler.this.harvesttime <= -1 && GameHandler.this.pvptime <= -1) {
    				GameHandler.this.EndGame();
	    		}
	    		else if(GameHandler.this.harvesttime <= -1 && GameHandler.this.pvptime >= 0){
	    			for (Player player : GameHandler.this.plugin.game.PlayersManager.getPlayers()) {
	    				if(GameHandler.this.pvptime == 60 || GameHandler.this.pvptime == 30 || GameHandler.this.pvptime == 10 || GameHandler.this.pvptime <= 5) {
	    					player.sendMessage(ChatColor.GRAY + "Fin du pvp dans "+ GameHandler.this.pvptime +" secondes !");
			    			player.playSound(player.getLocation(), Sound.BLOCK_DISPENSER_DISPENSE, 20, 1);
	    				}
		    			player.setLevel(GameHandler.this.pvptime);
	    			}
	    			GameHandler.this.pvptime--;
	    		}
	    		else {
	    			for (Player player : GameHandler.this.plugin.game.PlayersManager.getPlayers()) {
	    				if(GameHandler.this.harvesttime == 60 || GameHandler.this.harvesttime == 30 || GameHandler.this.harvesttime == 10 || GameHandler.this.harvesttime <= 5) {
	    					player.sendMessage(ChatColor.GRAY + "Fin de la récolte dans "+ GameHandler.this.harvesttime +" secondes !");
			    			player.playSound(player.getLocation(), Sound.BLOCK_DISPENSER_DISPENSE, 20, 1);
	    				}
		    			player.setLevel(GameHandler.this.harvesttime);		    			
	    			}
	    			GameHandler.this.harvesttime--;
	    			if(GameHandler.this.harvesttime == -1) {
	    				for (Player player : GameHandler.this.plugin.game.PlayersManager.getPlayers()) {
	    					player.sendMessage(ChatColor.GOLD + "Fin de la phase de récolte, vous avez maintenant "+pvptime+" secondes pour voler les pousses à vos ennemies en les frappant !");
	    					GameHandler.this.plugin.game.GameManager.setPvpStatus(true);
	    				}
	    			}
	    		}
	    	}
	    },0L,20L);
	}
	
	public void EndGame() {
		GameHandler.this.plugin.game.GameManager.setPvpStatus(false);
		Bukkit.getScheduler().cancelTask(this.runtaskid);
		for (Player player : this.plugin.game.PlayersManager.getPlayers()) {
			player.sendMessage(ChatColor.GOLD + "Fin de la partie, génération du classement...");
		}
		
		GenerateWinners();
		this.plugin.game.GameManager.setIsStart(false);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void GenerateWinners() {
		HashMap<Integer, Player> winnerTab = new HashMap();
		for (Player player : this.plugin.game.PlayersManager.getPlayers()) {
			winnerTab.put(this.plugin.game.PlayersManager.getNumberSaplings(player), player);
		}
		
		//Trie
		TreeMap<Integer,Player> sorted = new TreeMap<>(winnerTab);
		
		String[] ranking;
		int numberPlayers = this.plugin.game.PlayersManager.getNumberPlayers();
		ranking = new String[numberPlayers];
		
		// On renverse l'ordre en rentrant dans un nouveau array
		for (Entry<Integer, Player> entry : sorted.entrySet()) {
			ranking[numberPlayers-1] = entry.getValue().getName();			
			numberPlayers--;
		}
		// On affiche les 5 premiers
		for (Player player : this.plugin.game.PlayersManager.getPlayers()) {
			for (int i = 0; i < ranking.length && i < 5; i++) {
				player.sendMessage((i+1) +": "+ranking[i]);
			}
		}
		stopServer();
	}
	
	public void stopServer() {
		 this.runtaskid = Bukkit.getScheduler().scheduleSyncRepeatingTask(this.plugin, new Runnable() {
		    public void run() {
		    	if(GameHandler.this.stopCountDown <= -1) {
		    			GameHandler.this.plugin.getServer().shutdown();
		    	}
		    	else {
		    		GameHandler.this.stopCountDown--;
		    	}
		    }
		 },0L,20L);
	}
}
