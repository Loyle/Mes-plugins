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
		// On vérifie que le joueur a bien la permission (gràce à la fonction présente dans la class principale CutTheSaplings)
		if(CutTheSaplings.hasPermission(player, getPermission()).booleanValue()) {
			this.plugin.game.GameManager.setLobbySpawnLocation(player.getLocation());
			player.sendMessage(ChatColor.GREEN + "lobby définit avec succès");
		}
		return true;
	}

	// Message affiché lorsque l'on tape la commande /cts help
	@Override
	public String help(Player p) {
	    if (CutTheSaplings.hasPermission(p, getPermission()).booleanValue()) {
	        return "/cts setlobbyspawn - Définit le lobby";
	      }
	      return "";
	}

	// Permission nécessaire pour exécuter la commande
	@Override
	public String getPermission() {
		return "cts.admin";
	}
}
