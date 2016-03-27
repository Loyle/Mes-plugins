package com.gmail.loyle.shootcraft.game;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import com.gmail.loyle.shootcraft.ShootCraft;

public class ScoreboardManager {
	
	public ShootCraft plugin;
	private Scoreboard scoreboard;
	
	public ScoreboardManager(ShootCraft pl) {
		this.plugin = pl;
		
		this.renewScoreboard();
		
	}
	
	public Scoreboard getScoreboard() {
		return this.scoreboard;
	}
	public void renewScoreboard() {
		this.scoreboard = this.plugin.getServer().getScoreboardManager().getNewScoreboard();
	}
	
	public void reloadScoreboard() {
		String scoreboardName = "ShootCraft";
		Objective info = this.scoreboard.getObjective(ChatColor.GOLD + scoreboardName);	
		if(info != null) {
			info.unregister();
		}
		info = this.scoreboard.registerNewObjective(ChatColor.GOLD + scoreboardName, "dummy");
		info = this.scoreboard.getObjective(ChatColor.GOLD + scoreboardName);
		info.setDisplayName(ChatColor.GOLD + scoreboardName);
		info.setDisplaySlot(DisplaySlot.SIDEBAR);
		
		info.getScore(ChatColor.WHITE+"Joueurs: " + ChatColor.RED + this.plugin.game.PlayersManager.getNumberPlayers()).setScore(1);
		for(Player player : this.plugin.game.PlayersManager.getPlayers()) {
			player.setScoreboard(this.scoreboard);
		}
	}
}
