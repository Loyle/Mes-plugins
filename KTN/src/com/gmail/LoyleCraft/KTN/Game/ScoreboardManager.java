package com.gmail.LoyleCraft.KTN.Game;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import com.gmail.LoyleCraft.KTN.KTN;

public class ScoreboardManager {
	
	private Scoreboard sb = null;
	private NumberFormat formatter = new DecimalFormat("00");
	private String path = "KTN.";
	public KTN plugin;
	
	
	public ScoreboardManager(KTN plugin) {
		this.plugin = plugin;
		this.sb = Bukkit.getScoreboardManager().getNewScoreboard();
	}
	
	public void loadSidebar(int MinutesLeft, int SecondLeft, int Episode) {
		World KTN = Bukkit.getWorld(this.plugin.getConfig().getString(path+"Map.WorldName"));
		WorldBorder border = KTN.getWorldBorder();
		
		
		int wbsize = (int) border.getSize();
		int wbplus = wbsize/2;
		int wbmoins = -wbsize/2;
		
		Objective info = this.sb.getObjective(ChatColor.GOLD+this.plugin.getConfig().getString(path+"ScoreBoardTitle"));	
		if(info != null) {
			info.unregister();
		}
		info = this.sb.registerNewObjective(ChatColor.GOLD+this.plugin.getConfig().getString(path+"ScoreBoardTitle"), "dummy");
		info = this.sb.getObjective(ChatColor.GOLD+this.plugin.getConfig().getString(path+"ScoreBoardTitle"));
		info.setDisplayName(ChatColor.GOLD+this.plugin.getConfig().getString(path+"ScoreBoardTitle"));
		info.setDisplaySlot(DisplaySlot.SIDEBAR);
		
		info.getScore(ChatColor.WHITE+"Joueurs: " + ChatColor.RED + this.plugin.game.PlayersManager.getNumberPlayers()).setScore(5);
		info.getScore(ChatColor.WHITE+"Bordures: "+wbplus+" "+wbmoins).setScore(4);
		info.getScore(ChatColor.WHITE+"Episode: "+ ChatColor.RED + Episode).setScore(3);
		info.getScore("--------------------").setScore(2);
		info.getScore("Fin de l'épisode: " + ChatColor.RED+formatter.format(MinutesLeft)+ChatColor.WHITE+":"+ChatColor.RED+formatter.format(SecondLeft)).setScore(1);
		for(Player player : this.plugin.game.PlayersManager.getPlayers()) {
			player.setScoreboard(this.sb);
		}
	}
	@SuppressWarnings("deprecation")
	public void loadHealthStatus() {
		Objective vie = null;
		vie = this.sb.registerNewObjective("Vie", "health");
		vie = this.sb.getObjective("Vie");
		vie.setDisplayName("Vie");
		vie.setDisplaySlot(DisplaySlot.PLAYER_LIST);
		for(Player player : this.plugin.game.PlayersManager.getPlayers()) {
			player.setScoreboard(this.sb);
			vie.getScore(player).setScore(20);
		}
	}
	public Team createTeam(String TeamName, String DisplayName, String TeamColor, int TeamID) {
	    this.sb.registerNewTeam(TeamName);
	    
	    Team t = this.sb.getTeam(TeamName);
	    t.setDisplayName(DisplayName);
	    t.setCanSeeFriendlyInvisibles(true);
	    t.setAllowFriendlyFire(true);
	    t.setPrefix(TeamColor+"");
	    t.setSuffix(ChatColor.WHITE + "");
	    return t;
	}
	public Scoreboard getScoreboard() {
		return this.sb;
	}
	
}
