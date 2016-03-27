package com.gmail.LoyleCraft.CutTheSaplings.Commands;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.entity.Player;

import com.gmail.LoyleCraft.CutTheSaplings.CutTheSaplings;

public class SetLobbySpawn implements BasicCommand{
	
	private CutTheSaplings plugin;
	  
	public SetLobbySpawn(CutTheSaplings pl){
		this.plugin = pl;
	}

	// Gestionnaire de la commande setspawn
	@Override
	public boolean onCommand(Player player, String[] args) {
		// On v�rifie que le joueur a bien la permission (gr�ce � la fonction pr�sente dans la class principale CutTheSaplings)
		if(CutTheSaplings.hasPermission(player, getPermission()).booleanValue()) {
			this.plugin.game.GameManager.setLobbySpawnLocation(player.getLocation());
			player.sendMessage(ChatColor.GREEN + "lobby d�finit avec succ�s");
		}
		return true;
	}

	// Message affich� lorsque l'on tape la commande /cts help
	@Override
	public String help(Player p) {
	    if (CutTheSaplings.hasPermission(p, getPermission()).booleanValue()) {
	        return "/cts setlobbyspawn - D�finit le lobby";
	      }
	      return "";
	}

	// Permission n�cessaire pour ex�cuter la commande
	@Override
	public String getPermission() {
		return "cts.admin";
	}
}
