package com.gmail.LoyleCraft.KTP2135.Arena;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.gmail.LoyleCraft.KTP2135.KTP2135;

public class GameHandler {
	private KTP2135 plugin;
	private Arena arena;
	int runtaskid;
	int count;
	private int arenahandler;
	private String Grade;
	
	public GameHandler(KTP2135 pl, Arena arena) {
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
	    				player.sendMessage(ChatColor.YELLOW + "La partie démarre dans "+ GameHandler.this.count);
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
	
	Random rnd = new Random();
	public void startArena() {
	    this.arena.getStatusManager().setRunning(true);
	    this.arena.getStructureManager().setDamageEnabled(true);
	    this.plugin.signEditor.modifySigns(this.arena.getArenaName());
	    Action();
	    this.arenahandler = Bukkit.getScheduler().scheduleSyncRepeatingTask(this.plugin, new Runnable() {
	    	public void run() {
	    		if(GameHandler.this.arena.getPlayersManager().getCount() == 0) {
	    			GameHandler.this.stopArena();
	    			return;
	    		}
	    		if(GameHandler.this.arena.getPlayersManager().getCount() == 1) {
	    			GameHandler.this.arena.getStructureManager().setDamageEnabled(false);
	    			for(Player player : GameHandler.this.arena.getPlayersManager().getPlayersCopy()) {
	    				player.sendMessage(ChatColor.GREEN + "Félicitation vous avez gagné !");
	    				if(player.isOp()) {
	    					GameHandler.this.Grade = "Admin";
	    				}
	    				else {
	    					GameHandler.this. Grade = "Players";
	    				}
	    				GameHandler.this.plugin.getServer().dispatchCommand(GameHandler.this.plugin.getServer().getConsoleSender(), 
	    						"consolecoins addcoins KTP2135 "+player.getName()+" true 0 "+GameHandler.this.Grade);
	    				if(!player.isDead()) {
	    					arena.getPlayerHandler().leavePlayer(player);
	    				}
	    			}
	    		}
	    	}
	    }, 20L, 20L);
	}
	public void stopArena() {
	    for (Player player : this.arena.getPlayersManager().getAllParticipantsCopy()) {
	        this.arena.getPlayerHandler().leavePlayer(player);
	    }
	    this.arena.getStatusManager().setRunning(false);
	    this.plugin.signEditor.modifySigns(this.arena.getArenaName());
	    Bukkit.getScheduler().cancelTask(this.arenahandler);
	}
	
	public void Action() {
		for (Player player : GameHandler.this.arena.getPlayersManager().getPlayers()) {
			player.setMaxHealth(1);
			player.setFoodLevel(20);
			player.setSaturation(200000);
			player.sendMessage(ChatColor.YELLOW + "GOOO !");
		}
	}
}
