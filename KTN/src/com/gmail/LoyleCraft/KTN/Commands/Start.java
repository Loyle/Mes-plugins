package com.gmail.LoyleCraft.KTN.Commands;


import org.bukkit.entity.Player;

import com.gmail.LoyleCraft.KTN.KTN;

public class Start implements BasicCommand{
	
	private KTN plugin;
	  
	public Start(KTN pl){
		this.plugin = pl;
	}

	// Gestionnaire de la commande setspawn
	@Override
	public boolean onCommand(Player player, String[] args) {
		// On vérifie que le joueur a bien la permission (gràce à la fonction présente dans la class principale CutTheSaplings)
		if(KTN.hasPermission(player, getPermission()).booleanValue()) {
			this.plugin.game.GameManager.checkStart(player);
		}
		return true;
	}

	// Message affiché lorsque l'on tape la commande /cts help
	@Override
	public String help(Player p) {
	    if (KTN.hasPermission(p, getPermission()).booleanValue()) {
	        return "/ktn start - Démarre la partie";
	      }
	      return "";
	}

	// Permission nécessaire pour exécuter la commande
	@Override
	public String getPermission() {
		return "KTN.admin";
	}
}
