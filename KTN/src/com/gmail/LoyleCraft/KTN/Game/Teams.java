package com.gmail.LoyleCraft.KTN.Game;


import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

import com.gmail.LoyleCraft.KTN.KTN;

public class Teams {
	
	@SuppressWarnings("unused")
	private String path = "KTN.";
	private String TeamName;
	private int TeamID;
	private String DisplayName;
	private String TeamColor;
	private Team team;
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private ArrayList<Player> players = new ArrayList();
	
	public KTN plugin;
	
	public Teams(KTN plugin, String TeamName, String DisplayName, String TeamColor, int TeamID) {
		this.plugin = plugin;
		
		this.TeamName = TeamName;
		this.DisplayName = DisplayName;
		this.TeamColor = colorize(TeamColor);
		this.TeamID = TeamID;
		
		this.team = this.plugin.scoreboard.createTeam(this.TeamName, this.DisplayName, this.TeamColor, this.TeamID);
	}
	@SuppressWarnings("deprecation")
	public void addPlayer(Player player) {
		this.players.add(player);
		this.team.addPlayer(player);
		
		for(Player playerlist : this.plugin.getServer().getOnlinePlayers()) {
			playerlist.setScoreboard(this.plugin.scoreboard.getScoreboard());
		}
	}
	public String getName() {
		return this.TeamName;
	}
	public String getDisplayName() {
		return this.DisplayName;
	}
	public String getColor() {
		return this.TeamColor;
	}
	public ArrayList<Player> getPlayers() {
		return this.players;
	}
	public String colorize(String message) {
        return message.replaceAll("&([a-f0-9])", ChatColor.COLOR_CHAR + "$1");
    }
}
