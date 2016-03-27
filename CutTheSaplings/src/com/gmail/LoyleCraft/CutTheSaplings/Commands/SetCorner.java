package com.gmail.LoyleCraft.CutTheSaplings.Commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.gmail.LoyleCraft.CutTheSaplings.CutTheSaplings;

public class SetCorner implements BasicCommand{
	
	private CutTheSaplings plugin;
	  
	public SetCorner(CutTheSaplings pl){
		this.plugin = pl;
	}
	
	// Gestionnaire de la commande setcorner
	@Override
	public boolean onCommand(Player player, String[] args) {
		// On v�rifie que le joueur a bien la permission (gr�ce � la fonction pr�sente dans la class principale CutTheSaplings)
		if(CutTheSaplings.hasPermission(player, getPermission()).booleanValue()) {
			if(args[0].equalsIgnoreCase("1")) {
				Location l = player.getLocation();
				this.plugin.game.GameManager.setCorner(args[0], l);
				player.sendMessage(ChatColor.GREEN + "Angle 1 d�finit avec succ�s");
				
			}
			else if(args[0].equalsIgnoreCase("2")) {
				Location l = player.getLocation();
				this.plugin.game.GameManager.setCorner(args[0], l);
				player.sendMessage(ChatColor.GREEN + "Angle 2 d�finit avec succ�s");
			}
			else {
				player.sendMessage(ChatColor.RED + "Mauvais arguments, vous devez taper les arguments 1 ou 2");
			}
			return true;
		}
		return true;
	}

	// Message affich� lorsque l'on tape la commande /cts help
	@Override
	public String help(Player p) {
	    if (CutTheSaplings.hasPermission(p, getPermission()).booleanValue()) {
	        return "/cts setcorner 1/2 - D�finit les angles 1/2 de la map.";
	      }
	      return "";
	}
	
	// Permission n�cessaire pour ex�cuter la commande
	@Override
	public String getPermission() {
		return "cts.admin";
	}
}
