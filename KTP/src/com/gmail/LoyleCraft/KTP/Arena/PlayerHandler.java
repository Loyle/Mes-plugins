package com.gmail.LoyleCraft.KTP.Arena;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.ScoreboardManager;

import com.gmail.LoyleCraft.KTP.KTP;

public class PlayerHandler {
	private KTP plugin;
	  private Arena arena;
	  
	  public PlayerHandler(KTP pl, Arena arena)
	  {
	    this.plugin = pl;
	    this.arena = arena;
	  }
	  
	  public boolean checkJoin(Player player){
		  if (!this.arena.getStatusManager().isArenaEnabled()) {
			  player.sendMessage(ChatColor.RED + "L'arène n'est pas ouverte.");
			  return false;
		  }
		  if (this.arena.getStatusManager().isArenaRunning()) {
			  player.sendMessage(ChatColor.RED + "La partie est déjà démarrée.");
			  return false;
		  }
		  if (this.arena.getStatusManager().isArenaRegenerating()) {
			  player.sendMessage(ChatColor.RED + "L'arène est en court de régénération.");
			  return false;
		  }
		  if (this.arena.getPlayersManager().getCount() == this.arena.getStructureManager().getMaxPlayers()) {
			  player.sendMessage(ChatColor.RED + "La partie est pleinne.");
			  return false;
		  }
		  if(this.arena.getPlayersManager().isInArena(player.getName())) {
			  player.sendMessage(ChatColor.RED + "Vous êtes déjà dans une partie.");
			  return false;
		  }
	    return true;
	  }
	  
	  public void spawnPlayer(Player player) {
		  
		  this.plugin.Playerdt.storePlayerArmor(player);
		  this.plugin.Playerdt.storePlayerGameMode(player);
		  this.plugin.Playerdt.storePlayerHunger(player);
		  this.plugin.Playerdt.storePlayerInventory(player);
		  this.plugin.Playerdt.storePlayerPotionEffects(player);
		  
		  player.updateInventory();
		  
		  Location l = this.arena.getStructureManager().getSpawnPoint();
		  player.teleport(l);
		  
		  player.setFlying(false);
		  player.setAllowFlight(false);
		  player.setGameMode(GameMode.ADVENTURE);
		  
		  player.setLevel(0);
		  
		  player.sendMessage(ChatColor.YELLOW + "Vous avez rejoint la file d'attente, /KTP leave pour quitter la file.");
		  for (Player otherp : this.arena.getPlayersManager().getPlayers()) {
			  int intogame = this.arena.getPlayersManager().getCount();
			  intogame++;
			  otherp.sendMessage(ChatColor.GREEN +""+ player.getName() +" a rajoint la partie ("+ intogame +"/"+this.arena.getStructureManager().getMaxPlayers()+")");
		  }
		  this.arena.getPlayersManager().addPlayer(player);
		  this.plugin.signEditor.modifySigns(this.arena.getArenaName());
		  if ((!this.arena.getStatusManager().isArenaStarting()) && (this.arena.getPlayersManager().getCount() == this.arena.getStructureManager().getMinPlayers())) {
		        this.arena.getGameHandler().runArenaCountdown();
		  }
	  }
	public void leavePlayer(Player player) {
		  boolean spectator = this.arena.getPlayersManager().isSpectator(player.getName());
		  if (spectator) {
			  this.arena.getPlayersManager().removeSpectator(player);
			  player.setAllowFlight(false);
		      player.setFlying(false);
		  }
		  else {
			  this.arena.getPlayersManager().removePlayer(player);
		  }
		  for (Player otherp : this.arena.getPlayersManager().getPlayers()) {
			  int intogame = this.arena.getPlayersManager().getCount();
			  otherp.sendMessage(ChatColor.GREEN +""+ player.getName() +" s'est enfuis ("+ intogame +"/"+this.arena.getStructureManager().getMaxPlayers()+")");
		  }
		  this.plugin.signEditor.modifySigns(this.arena.getArenaName());

		  this.plugin.Playerdt.restorePlayerArmor(player);
		  this.plugin.Playerdt.restorePlayerGameMode(player);
		  this.plugin.Playerdt.restorePlayerHunger(player);
		  this.plugin.Playerdt.restorePlayerInventory(player);
		  this.plugin.Playerdt.restorePlayerPotionEffects(player);
		  
		  player.updateInventory();
		  
		  Location l = this.plugin.amanager.getLobbySpawn();
		  player.teleport(l);
		  
		  player.setLevel(0);
		  
		  player.setMaxHealth(20);
		  player.setHealth(20);
		  
		  ScoreboardManager scmanager = Bukkit.getScoreboardManager();
		  player.setScoreboard(scmanager.getNewScoreboard());
		  
		  player.sendMessage(ChatColor.YELLOW + "Vous avez quitté la partie.");
	  }
}
