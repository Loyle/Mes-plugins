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
		// On v�rifie que le joueur a bien la permission (gr�ce � la fonction pr�sente dans la class principale CutTheSaplings)
		if(KTN.hasPermission(player, getPermission()).booleanValue()) {
			this.plugin.game.GameManager.checkStart(player);
		}
		return true;
	}

	// Message affich� lorsque l'on tape la commande /cts help
	@Override
	public String help(Player p) {
	    if (KTN.hasPermission(p, getPermission()).booleanValue()) {
	        return "/ktn start - D�marre la partie";
	      }
	      return "";
	}

	// Permission n�cessaire pour ex�cuter la commande
	@Override
	public String getPermission() {
		return "KTN.admin";
	}
}
