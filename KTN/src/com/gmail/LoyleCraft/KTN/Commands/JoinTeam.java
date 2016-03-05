package com.gmail.LoyleCraft.KTN.Commands;


import net.md_5.bungee.api.ChatColor;

import org.bukkit.entity.Player;

import com.gmail.LoyleCraft.KTN.KTN;

public class JoinTeam implements BasicCommand{
	
	private KTN plugin;
	  
	public JoinTeam(KTN pl){
		this.plugin = pl;
	}

	// Gestionnaire de la commande setspawn
	@Override
	public boolean onCommand(Player player, String[] args) {
		if(args.length == 1 && !args.equals(" ") && this.plugin.game.GameManager.getIsStart() == false) {
			this.plugin.game.TeamsManager.joinTeam(Integer.valueOf(args[0]), player);
		}
		else {
			player.sendMessage(ChatColor.RED + "Il manque l'ID de la team / Partie déjà en cours");
		}
		return true;
	}

	@Override
	public String help(Player p) {
	    if (KTN.hasPermission(p, getPermission()).booleanValue()) {
	        return "/ktn jointeam <id> - Rejoindre une team";
	      }
	      return "";
	}

	// Permission nécessaire pour exécuter la commande
	@Override
	public String getPermission() {
		return "KTN.admin";
	}
}
