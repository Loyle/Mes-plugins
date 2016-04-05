package fr.loyle.shootcraft.game;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.NameTagVisibility;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import fr.loyle.shootcraft.ShootCraft;

public class ScoreboardManager {
	
	private ShootCraft plugin;
	private Scoreboard scoreboard;
	private Team team;
	
	public ScoreboardManager(ShootCraft pl) {
		this.plugin = pl;
		
		this.renewScoreboard();	
	}
	
	public Scoreboard getScoreboard() {
		return this.scoreboard;
	}
	public void renewScoreboard() {
		this.scoreboard = this.plugin.getServer().getScoreboardManager().getNewScoreboard();
		
		this.scoreboard.registerNewTeam("NoName");
		this.team = this.scoreboard.getTeam("NoName");
		this.team.setNameTagVisibility(NameTagVisibility.NEVER);
		this.team.setAllowFriendlyFire(false);
		this.team.setCanSeeFriendlyInvisibles(false);
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
		
		for(Player player: this.plugin.game.getPlayersManager().getPlayers()) {
			info.getScore(player.getName()).setScore(this.plugin.game.getScoreManager().getScore(player));
		}
		
		
		
		
		
		for(Player player : this.plugin.game.getPlayersManager().getPlayers()) {
			player.setScoreboard(this.scoreboard);
		}
	}
	
	public void joinTeam(Player player) {
		this.team.addEntry(player.getName());
	}
	public void leaveTeam(Player player) {
		this.team.removeEntry(player.getName());
	}
}
