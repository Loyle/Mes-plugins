package com.gmail.LoyleCraft.KTP2135.Commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.gmail.LoyleCraft.KTP2135.KTP2135;

public class InfoCommand implements BasicCommand {

	@SuppressWarnings("unused")
	private KTP2135 plugin;
	  
	public InfoCommand(KTP2135 pl){
		this.plugin = pl;
	}

	@SuppressWarnings("unused")
	@Override
	public boolean onCommand(Player player, String[] args) {
		if(KTP2135.hasPermission(player, getPermission()).booleanValue()) {
			if(args.length != 1) {
				player.sendMessage(ChatColor.RED + "Merci d'indiquer le nom de votre map.");
				return true;
			}
			else {
				if(true) {
					player.sendMessage("Fonctionnalité pas encore disponible !");
					return true;
				}
				else {
					player.sendMessage(ChatColor.RED + "Cette map n'existe pas !");
					return true;
				}
			}
		}
		else {
			player.sendMessage(ChatColor.RED + "Vous n'avez pas la permission !");
			return true;
		}
	}

	@Override
	public String help(Player p) {
	    if (KTP2135.hasPermission(p, getPermission()).booleanValue()) {
	        return "/ktp2135 info [Arena name] - Info of arena.";
	      }
	      return "";
	}

	@Override
	public String getPermission() {
		return "KTP2135.edit";
	}
}
