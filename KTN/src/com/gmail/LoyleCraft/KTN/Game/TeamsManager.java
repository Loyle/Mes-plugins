package com.gmail.LoyleCraft.KTN.Game;


import net.md_5.bungee.api.ChatColor;

import org.bukkit.entity.Player;

import com.gmail.LoyleCraft.KTN.KTN;

public class TeamsManager {
	private String path = "KTN.";
	private Teams[] TeamsList;
	public KTN plugin;

	public TeamsManager(KTN pl) {
		this.plugin = pl;
		
		this.TeamsList = new Teams[this.plugin.getConfig().getInt(path+"Teams.Numbers")];
		
		for(int i = 1 ; i <= this.plugin.getConfig().getInt(path+"Teams.Numbers") ; i++) {
			System.out.println("[KTN] Load de la team numero "+i);
			this.TeamsList[i-1] = new Teams(this.plugin, this.plugin.getConfig().getString(path+"TeamsList."+i+".Name"), this.plugin.getConfig().getString(path+"TeamsList."+i+".Name"), this.plugin.getConfig().getString(path+"TeamsList."+i+".Color"), i);
		}
	}
	public void joinTeam(int teamID, Player player) {
		this.TeamsList[teamID-1].addPlayer(player);
		player.sendMessage(ChatColor.GOLD + "Vous avez rejoint la team " + this.TeamsList[teamID-1].getDisplayName());
	}
	public Teams[] getTeams() {
		return this.TeamsList;
	}
	public String getName(int teamID) {
		return this.TeamsList[teamID-1].getDisplayName();
	}
	public String getColor(int teamID) {
		return this.TeamsList[teamID-1].getColor();
	}
}
