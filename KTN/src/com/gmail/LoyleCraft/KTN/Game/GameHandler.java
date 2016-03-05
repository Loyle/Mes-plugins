package com.gmail.LoyleCraft.KTN.Game;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scoreboard.Scoreboard;

import com.gmail.LoyleCraft.KTN.KTN;

public class GameHandler {
	
	public KTN plugin;
	private String path = "KTN.";
	private int runtaskid;
	private int count;
	private int spawnID = 1;
	private Scoreboard sb = null;
	private int MinutesLeft = 0;
    private int SecondLeft = 0;
	private int Episode = 1;
	private int LifeProtect = 60;
	
	public GameHandler(KTN pl) {
		this.plugin = pl;
		this.count = this.plugin.getConfig().getInt(path + "StartCountDown");
	}
	
	// Countdown avant départ de la partie
	public void runGameCountdown() {
		// On précise au plugin que la partie est en cours de démarrage
		this.plugin.game.GameManager.setIsStarting(true);
	    this.runtaskid = Bukkit.getScheduler().scheduleSyncRepeatingTask(this.plugin, new Runnable() {
	    	public void run() {
	    		if (GameHandler.this.count == -1) {
	    			// Si le countdown est arrivé à 0 (enfin à -1) on stop le countdown, et on démarre la partie
	    			GameHandler.this.stopGameCountdown();
	    			if(GameHandler.this.plugin.getConfig().getBoolean(path+"PlayWithTeams")) {
	    				GameHandler.this.stopGameCountdown();
	    				GameHandler.this.startGameTeam();
	    			}
	    			else {
	    				GameHandler.this.stopGameCountdown();
	    				GameHandler.this.startGameSolo();
	    			}
	    	        
	    	        GameHandler.this.count = GameHandler.this.plugin.getConfig().getInt(path + "StartCountDown");
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
	public void startGameSolo() {
		this.plugin.scoreboard.loadHealthStatus();
		this.plugin.game.GameManager.setIsStart(true);
		this.plugin.game.GameManager.setDamageStatus(false);
		this.MinutesLeft = this.plugin.getConfig().getInt(path+"EpisodeTime");
		iniScoreboard();
		
		for (Player player : this.plugin.game.PlayersManager.getPlayers()) {
			addToScoreboard(player);
			player.setFoodLevel(20);
			player.setHealth(20.0);
			player.getInventory().clear();
			player.setGameMode(GameMode.SURVIVAL);
			for (PotionEffect effect : player.getActivePotionEffects()) {
		        player.removePotionEffect(effect.getType());
			}
			
			Location l = getSpawn();
			player.teleport(l);
			
			
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title "+player.getName()+" times 5 20 5");
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title "+player.getName()+" subtitle {\"text\":\"Bonne chance\",\"color\":\"gold\"}");
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title "+player.getName()+" title {\"text\":\"C'est parti !\",\"color\":\"red\"}");
			
			
		}
		

		this.runtaskid = Bukkit.getScheduler().scheduleSyncRepeatingTask(this.plugin, new Runnable() {
	    	public void run() {
	    		if(GameHandler.this.plugin.game.PlayersManager.getNumberPlayers() == 1) {
	    			GameHandler.this.EndGame();
	    		}
	    		else if(GameHandler.this.plugin.game.PlayersManager.getNumberPlayers() <= 0) {
	    			Bukkit.broadcastMessage(ChatColor.RED + "ERREUR -> Plus aucun joueurs, impossible de trouver un gagnant -> Toto est désigné gagnant !");
	    			GameHandler.this.StopGame();
	    		}
	    		
	    		/*for (Player player : GameHandler.this.plugin.game.PlayersManager.getPlayers()) {
	    			GameHandler.this.plugin.game.PlayersManager.CheckPotions(player);
	    		}*/
	    		
	    		
	    		iniScoreboard();
	    		GameHandler.this.LifeProtect--;
	    		GameHandler.this.SecondLeft--;
	    		if(GameHandler.this.SecondLeft <= -1) {
	    			GameHandler.this.SecondLeft = 59;
	    			GameHandler.this.MinutesLeft--;
	    		}
	    		if(GameHandler.this.MinutesLeft <= -1) {
	    			GameHandler.this.MinutesLeft = GameHandler.this.plugin.getConfig().getInt(path+"EpisodeTime");			
	    			for (Player player : GameHandler.this.plugin.game.PlayersManager.getPlayers()) {
	    				player.sendMessage(ChatColor.AQUA + "Fin de l'épisode "+GameHandler.this.Episode);
	    			}
	    			GameHandler.this.Episode++;
	    		}
	    		if(GameHandler.this.Episode == GameHandler.this.plugin.getConfig().getInt(path+"WallReduce.Episode")) {
	    			startWallReduce();
	    		}
	    		if(GameHandler.this.LifeProtect == -1) {
	    			GameHandler.this.plugin.game.GameManager.setDamageStatus(true);
	    			for (Player player : GameHandler.this.plugin.game.PlayersManager.getPlayers()) {
	    				player.sendMessage(ChatColor.GOLD + "Dégats maintenant activés");
	    			}
	    		}
	    	}
	    },0L,20L);
	}
	public void startGameTeam() {
		this.plugin.scoreboard.loadHealthStatus();
		this.plugin.game.GameManager.setIsStart(true);
		this.plugin.game.GameManager.setDamageStatus(false);
		this.MinutesLeft = this.plugin.getConfig().getInt(path+"EpisodeTime");
		iniScoreboard();
		
		for (Player player : this.plugin.game.PlayersManager.getPlayers()) {
			player.setFoodLevel(20);
			player.setHealth(20.0);
			player.getInventory().clear();
			player.setGameMode(GameMode.SURVIVAL);
			for (PotionEffect effect : player.getActivePotionEffects()) {
		        player.removePotionEffect(effect.getType());
			}
			
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title "+player.getName()+" times 5 20 5");
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title "+player.getName()+" subtitle {\"text\":\"Bonne chance\",\"color\":\"gold\"}");
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title "+player.getName()+" title {\"text\":\"C'est partie !\",\"color\":\"red\"}");
			
			
		}
		
		Teams[] TeamsList = this.plugin.game.TeamsManager.getTeams();
		for (int i = 1; i <= this.plugin.getConfig().getInt(path+"Teams.Numbers") ; i++) {
			Location l = getSpawn();
			if(TeamsList[i-1].getPlayers() != null) {
				for (Player player : TeamsList[i-1].getPlayers()) {
					player.teleport(l);
				}
			}
		}
		
		this.runtaskid = Bukkit.getScheduler().scheduleSyncRepeatingTask(this.plugin, new Runnable() {
	    	public void run() {
	    		if(GameHandler.this.plugin.game.PlayersManager.getNumberPlayers() == 1) {
	    			GameHandler.this.EndGame();
	    		}
	    		else if(GameHandler.this.plugin.game.PlayersManager.getNumberPlayers() <= 0) {
	    			Bukkit.broadcastMessage(ChatColor.RED + "ERREUR -> Plus aucun joueurs, impossible de trouver un gagnant -> Toto est désigné gagnant !");
	    			GameHandler.this.StopGame();
	    		}
	    		
	    		/*for (Player player : GameHandler.this.plugin.game.PlayersManager.getPlayers()) {
	    			GameHandler.this.plugin.game.PlayersManager.CheckPotions(player);
	    		}*/
	    		
	    		iniScoreboard();
	    		GameHandler.this.LifeProtect--;
	    		GameHandler.this.SecondLeft--;
	    		if(GameHandler.this.SecondLeft <= -1) {
	    			GameHandler.this.SecondLeft = 59;
	    			GameHandler.this.MinutesLeft--;
	    		}
	    		if(GameHandler.this.MinutesLeft <= -1) {
	    			GameHandler.this.MinutesLeft = GameHandler.this.plugin.getConfig().getInt(path+"EpisodeTime");			
	    			for (Player player : GameHandler.this.plugin.game.PlayersManager.getPlayers()) {
	    				player.sendMessage(ChatColor.AQUA + "Fin de l'épisode "+GameHandler.this.Episode);
	    			}
	    			GameHandler.this.Episode++;
	    		}
	    		if(GameHandler.this.Episode == GameHandler.this.plugin.getConfig().getInt(path+"WallReduce.Episode")) {
	    			startWallReduce();
	    		}
	    		if(GameHandler.this.LifeProtect == -1) {
	    			GameHandler.this.plugin.game.GameManager.setDamageStatus(true);
	    			for (Player player : GameHandler.this.plugin.game.PlayersManager.getPlayers()) {
	    				player.sendMessage(ChatColor.GOLD + "Dégats maintenant activés");
	    			}
	    		}
	    	}
	    },0L,20L);
	}
	
	public void StopGame() {
		this.plugin.game.GameManager.setPvpStatus(false);
		Bukkit.getScheduler().cancelTask(this.runtaskid);
		this.plugin.game.GameManager.setIsStart(false);
	}
	public void EndGame() {
		this.plugin.game.GameManager.setPvpStatus(false);
		Bukkit.getScheduler().cancelTask(this.runtaskid);
		for (Player player : this.plugin.game.PlayersManager.getPlayers()) {
			player.sendMessage(ChatColor.GOLD + "Fin de la partie, vous avez gagner");
		}
		Bukkit.broadcastMessage(ChatColor.GOLD + "KTN terminé, merci d'avoir participé");
		this.plugin.game.GameManager.setIsStart(false);
	}
	
	public Location getSpawn() {
		Double X = this.plugin.getConfig().getDouble(path+"Spawns."+this.spawnID+".x");
		Double Y = (double) 200;
		Double Z = this.plugin.getConfig().getDouble(path+"Spawns."+this.spawnID+".z");
		Float Yaw = (float) 0;
		Float Pitch = (float) 0;
		World World = Bukkit.getServer().getWorld(this.plugin.getConfig().getString(path+"Map.WorldName"));
		
		this.spawnID++;
		
		Location l = new Location(World, X, Y, Z, Yaw, Pitch);
		return l;
	}
	
	public void iniScoreboard() {
		this.plugin.scoreboard.loadSidebar(this.MinutesLeft, this.SecondLeft, this.Episode);
	}
	public void addToScoreboard(Player player) {
		player.setScoreboard(sb);
		this.updatePlayerListName(player);
	}
	public void updatePlayerListName(Player p) {
		p.setScoreboard(sb);
	}
	public void startWallReduce() {
		World KTN = Bukkit.getWorld(this.plugin.getConfig().getString(path+"Map.WorldName"));
		WorldBorder border = KTN.getWorldBorder();
		if(border.getSize() == this.plugin.getConfig().getInt(path+"Map.Size")) {
			border.setSize(this.plugin.getConfig().getDouble(path+"WallReduce.Size"), 60 * this.plugin.getConfig().getLong(path+"WallReduce.TimeForReduce"));
			for(Player player : GameHandler.this.plugin.game.PlayersManager.getPlayers()) {
				player.sendMessage(ChatColor.RED + "-------------- Attention --------------");
				player.sendMessage(ChatColor.RED + "Le mur démarre sa diminution !");
			}
		}
	}
}
