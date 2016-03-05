package com.gmail.LoyleCraft.KTN.Commands;


import net.md_5.bungee.api.ChatColor;

import org.bukkit.entity.Player;

import com.gmail.LoyleCraft.KTN.KTN;

public class SetLobby implements BasicCommand{
	
	private KTN plugin;
	  
	public SetLobby(KTN pl){
		this.plugin = pl;
	}

	// Gestionnaire de la commande setspawn
	@Override
	public boolean onCommand(Player player, String[] args) {
		
		// On vérifie que le joueur a bien la permission (gràce à la fonction présente dans la class principale CutTheSaplings)
		if(KTN.hasPermission(player, getPermission()).booleanValue()) {
			this.plugin.game.GameManager.setLobbySpawnLocation(player.getLocation());
			player.sendMessage(ChatColor.GREEN + "lobby définit avec succès");
		}
		return true;
	}

	// Message affiché lorsque l'on tape la commande /cts help
	@Override
	public String help(Player p) {
	    if (KTN.hasPermission(p, getPermission()).booleanValue()) {
	        return "/ktn setlobby - Définit le lobby";
	      }
	      return "";
	}

	// Permission nécessaire pour exécuter la commande
	@Override
	public String getPermission() {
		return "KTN.admin";
	}
}
