package com.gmail.LoyleCraft.KTP.Arena;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import com.gmail.LoyleCraft.KTP.KTP;


public class GameHandler {
	private KTP plugin;
	private Arena arena;
	int runtaskid;
	int count;
	private int arenahandler;
	private String Grade;
    private Double x = 0d;
    private Double y = 200d;
    private Double z = 0d;
    private Float yaw = 90f;
    private Float pitch = 90f;
    private int Spawn = 1;
    private Scoreboard sb = null;
    private Integer MinutesLeft = 0;
    private Integer SecondLeft = 0;
    private Integer Episode = 0;
    private NumberFormat formatter = new DecimalFormat("00");
	
	public GameHandler(KTP pl, Arena arena) {
		this.plugin = pl;
	    this.arena = arena;
		this.count = arena.getStructureManager().getCountDown();
	}
	public void runArenaCountdown() {
		this.arena.getStatusManager().setStarting(true);
		this.arena.getStructureManager().setDamageEnabled(false);
		this.plugin.signEditor.modifySigns(this.arena.getArenaName());
		
	    this.runtaskid = Bukkit.getScheduler().scheduleSyncRepeatingTask(this.plugin, new Runnable() {
	    	public void run() {
	    		if (GameHandler.this.arena.getPlayersManager().getCount() < GameHandler.this.arena.getStructureManager().getMinPlayers()) {
	    			GameHandler.this.stopArenaCountdown();
	    		}
	    		else if (GameHandler.this.count == -1) {
	    			GameHandler.this.stopArenaCountdown();
	    	        GameHandler.this.startArena();
	    		}
	    		else {
	    			for (Player player : GameHandler.this.arena.getPlayersManager().getPlayers()) {
	    				if(GameHandler.this.count == 60 || GameHandler.this.count == 30 || GameHandler.this.count == 20 || GameHandler.this.count <= 5 || GameHandler.this.count == 10) {
	    					player.sendMessage(ChatColor.YELLOW + "La partie démarre dans "+ ChatColor.GOLD + GameHandler.this.count);
	    					player.playSound(player.getLocation(), Sound.SUCCESSFUL_HIT, 20, 1);
	    				}
	    				player.setLevel(GameHandler.this.count);
	    			}
	    			GameHandler.this.count--;
	    		}
	    	}
	    },0L,20L);
	}
	
	public void stopArenaCountdown() {
		this.arena.getStatusManager().setStarting(false);
		this.plugin.signEditor.modifySigns(this.arena.getArenaName());
	    this.count = this.arena.getStructureManager().getCountDown();
	    Bukkit.getScheduler().cancelTask(this.runtaskid);
	}
	
	public void startArena() {
	    this.arena.getStatusManager().setRunning(true);
	    this.plugin.signEditor.modifySigns(this.arena.getArenaName());
	    
	    World KTP = this.plugin.getServer().getWorld(this.arena.getArenaName());
	    if(KTP == null) {
	    	createWorld();
	    }
	    
	    KTP.setGameRuleValue("naturalRegeneration", "false");
	    KTP.setGameRuleValue("doDaylightCycle", "false");
	    KTP.setTime(6000);
	    KTP.setStorm(false);
	    
	    Location wcenter = new Location(KTP,0D,0D,0D);
	    WorldBorder wb = KTP.getWorldBorder();
	    wb.setCenter(wcenter);
	    wb.setSize(2000);
	    wb.setDamageAmount(1);
	    wb.setWarningDistance(100);
	    
	    this.SecondLeft = 0;
	    this.MinutesLeft = 20;
	    this.Episode = 1;
	    
	    iniScoreboard();
	    
		for (Player player : GameHandler.this.arena.getPlayersManager().getPlayers()) {
			addToScoreboard(player);
			Location l = getcustomSpawn();
			player.teleport(l);
			player.setGameMode(GameMode.SURVIVAL);
			player.setHealth(20);
			player.setSaturation(20);
			player.setFoodLevel(20);
			player.sendMessage(ChatColor.YELLOW + "GOOO !");
			player.sendMessage(ChatColor.GREEN + "Vous avez 30s d'invincibilité");
		}
		
	    Bukkit.getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable() {
		      public void run() {
		        for (Player player : GameHandler.this.arena.getPlayersManager().getPlayers()) {
		        	GameHandler.this.arena.getStructureManager().setDamageEnabled(true);
		        	player.sendMessage(ChatColor.RED + "L'invincibilité est terminé !");
		        }
		    }
		}, 20L*30);
		
	    this.arenahandler = Bukkit.getScheduler().scheduleSyncRepeatingTask(this.plugin, new Runnable() {
	    	public void run() {
	    		if(GameHandler.this.arena.getPlayersManager().getCount() == 0) {
	    			World w = Bukkit.getWorld(arena.getArenaName());
	    			World w_nether = Bukkit.getWorld(arena.getArenaName()+"_nether");
	    			if(w.getPlayers().isEmpty() && w_nether.getPlayers().isEmpty()) {
	    				GameHandler.this.stopArena();
	    				return;
	    			}
	    			else {
	    				Location respawn = GameHandler.this.plugin.amanager.getLobbySpawn();
	    				for (Player quitp : w.getPlayers()) {
	    					quitp.teleport(respawn);
	    				}
	    				for (Player quitp : w_nether.getPlayers()) {
	    					quitp.teleport(respawn);
	    				}
	    			}
	    		}
	    		if(GameHandler.this.arena.getPlayersManager().getCount() == 1) {
	    			GameHandler.this.arena.getStructureManager().setDamageEnabled(false);
	    			for(Player player : GameHandler.this.arena.getPlayersManager().getPlayersCopy()) {
	    				player.sendMessage(ChatColor.GREEN + "Félicitation vous avez gagné !");
	    				GameHandler.this.arena.getPlayerHandler().leavePlayer(player);
	    				
	    				if(player.isOp()) {
	    					GameHandler.this.Grade = "Admin";
	    				}
	    				else {
	    					GameHandler.this. Grade = "Players";
	    				}
	    				GameHandler.this.plugin.getServer().dispatchCommand(GameHandler.this.plugin.getServer().getConsoleSender(), 
	    						"consolecoins addcoins KTP "+player.getName()+" true 0 "+GameHandler.this.Grade);
	    			}
	    		}
	    		iniScoreboard();
	    		SecondLeft--;
	    		if(SecondLeft == -1) {
	    			MinutesLeft--;
	    			SecondLeft = 59;
	    		}
	    		if(MinutesLeft == -1) {
	    			MinutesLeft = 20;
	    			SecondLeft = 0;
	    			for(Player player : GameHandler.this.arena.getPlayersManager().getPlayersCopy()) {
	    				player.sendMessage(ChatColor.GREEN + "Fin de l'épisode " +
	    						ChatColor.GOLD + Episode);
	    			}
	    			Episode++;
	    		}
	    		
	    		if(Episode == 3) {
	    			StartWorldBorderDiminu();
	    		}
	    	}
	    }, 20L, 20L);
	}
	
	public void stopArena() {
	    for (Player player : this.arena.getPlayersManager().getAllParticipantsCopy()) {
	        this.arena.getPlayerHandler().leavePlayer(player);
	    }
	    Bukkit.getScheduler().cancelTask(this.arenahandler);
	    
	    this.arena.getStatusManager().setRunning(false);
	    this.arena.getStatusManager().setRegenerating(true);
	    this.plugin.signEditor.modifySigns(this.arena.getArenaName());
	    
		this.startArenaRegen();
	}
	
	private void startArenaRegen() {
		this.plugin.getServer().broadcastMessage(ChatColor.RED + "Attention, la map KTP "+this.arena.getArenaName()+" est en cours de génération, il va y avoir un lag de moins de 20s, merci de votre compréhension.");
		this.arena.getStatusManager().setRegenerating(true);
		this.plugin.signEditor.modifySigns(this.arena.getArenaName());
		
		deleteWorld();
		createWorld();
		this.arena.getStatusManager().setRegenerating(false);
		this.plugin.signEditor.modifySigns(GameHandler.this.arena.getArenaName());

	}
	public void createWorld() {
		this.plugin.getServer().dispatchCommand(this.plugin.getServer().getConsoleSender(), "mv create "+this.arena.getArenaName()+" normal");
		this.plugin.getServer().dispatchCommand(this.plugin.getServer().getConsoleSender(), "mv create "+this.arena.getArenaName()+"_nether nether");
	}
	public void deleteWorld() {
		this.plugin.getServer().dispatchCommand(this.plugin.getServer().getConsoleSender(), "mv delete "+this.arena.getArenaName());
		this.plugin.getServer().dispatchCommand(this.plugin.getServer().getConsoleSender(), "mvconfirm");
		this.plugin.getServer().dispatchCommand(this.plugin.getServer().getConsoleSender(), "mv delete "+this.arena.getArenaName()+"_nether");
		this.plugin.getServer().dispatchCommand(this.plugin.getServer().getConsoleSender(), "mvconfirm");
	}
	  
	public Location getcustomSpawn() {
		World w = this.plugin.getServer().getWorld(this.arena.getArenaName());
		String pathspawn = "SpawnMap."+this.Spawn;
		this.x = this.plugin.getConfig().getDouble(pathspawn+".x");
		this.z = this.plugin.getConfig().getDouble(pathspawn+".z");
		  
		this.Spawn++;
		Location l = new Location(w, this.x, this.y, this.z, this.yaw, this.pitch);
		return l;
	}
	
	@SuppressWarnings({ "deprecation"})
	public void iniScoreboard() {
		World KTP = Bukkit.getWorld(arena.getArenaName());
		WorldBorder wb = KTP.getWorldBorder();
		Objective vie = null;
		Objective info = null;
		try {
			info = sb.getObjective(ChatColor.GOLD+"KTP");
			info.setDisplaySlot(null);
			info.unregister();
		} catch (Exception e) {
			
		}
		int wbsize = (int) wb.getSize();
		int wbplus = wbsize/2;
		int wbmoins = -wbsize/2;
		
		sb = Bukkit.getServer().getScoreboardManager().getNewScoreboard();
		vie = sb.registerNewObjective("Vie", "health");
		vie = sb.getObjective("Vie");
		vie.setDisplayName("Vie");
		vie.setDisplaySlot(DisplaySlot.PLAYER_LIST);
		
		info = sb.registerNewObjective(ChatColor.GOLD+"KTP", "dummy");
		info = sb.getObjective(ChatColor.GOLD+"KTP");
		info.setDisplayName(ChatColor.GOLD+"KTP");
		info.setDisplaySlot(DisplaySlot.SIDEBAR);
		info.getScore(Bukkit.getOfflinePlayer(
				ChatColor.WHITE+"Joueurs: " + ChatColor.RED + arena.getPlayersManager().getCount())).setScore(5);
		info.getScore(Bukkit.getOfflinePlayer(ChatColor.WHITE+"Bordures: "+wbplus+" "+wbmoins)).setScore(4);
		info.getScore(Bukkit.getOfflinePlayer(ChatColor.WHITE+"Episode: "+ ChatColor.RED + this.Episode)).setScore(3);
		info.getScore(Bukkit.getOfflinePlayer("--------------------")).setScore(2);
		info.getScore(Bukkit.getOfflinePlayer("Fin de l'épisode: " + ChatColor.RED+formatter.format(this.MinutesLeft)+ChatColor.WHITE+":"+ChatColor.RED+formatter.format(this.SecondLeft))).setScore(1);
		
		for(Player player : GameHandler.this.arena.getPlayersManager().getPlayersCopy()) {
			updatePlayerListName(player);
		}
	}
	
	@SuppressWarnings("deprecation")
	public void addToScoreboard(Player player) {
		player.setScoreboard(sb);
		sb.getObjective("Vie").getScore(player).setScore(0);
		this.updatePlayerListName(player);
	}
	public void updatePlayerListName(Player p) {
		p.setScoreboard(sb);
	}
	
	public void StartWorldBorderDiminu() {
		World KTP = Bukkit.getWorld(arena.getArenaName());
		WorldBorder wb = KTP.getWorldBorder();
		if(wb.getSize() == 2000) {
			wb.setSize(400, 20*120);
			for(Player player : GameHandler.this.arena.getPlayersManager().getPlayersCopy()) {
				player.sendMessage(ChatColor.RED+"Attention, les bordures commence à retrecire (2h00 pour arriver en 200 -200)");
			}
		}
	}
}
