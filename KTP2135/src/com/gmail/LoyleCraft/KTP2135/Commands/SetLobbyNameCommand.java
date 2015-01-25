package com.gmail.LoyleCraft.KTP2135.Commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.gmail.LoyleCraft.KTP2135.KTP2135;

public class SetLobbyNameCommand  implements BasicCommand {
	
	@SuppressWarnings("unused")
	private KTP2135 plugin;
	public SetLobbyNameCommand(KTP2135 pl){
		this.plugin = pl;
	}

	@SuppressWarnings("unused")
	@Override
	public boolean onCommand(Player player, String[] args) {
		if(KTP2135.hasPermission(player, getPermission()).booleanValue()) {
			if(args.length != 2) {
				player.sendMessage(ChatColor.RED + "Il manque le nom de la map ou le nom dans le lobby");
				return true;
			}
			else {
				if(args[0] != null && args[1] != null ) {
					if(true) {
						player.sendMessage(ChatColor.YELLOW + "Le lobby name de la map "+args[0]+" a bien été modifié !");
						return true;
					}
					else {
						player.sendMessage(ChatColor.RED + "Cette map n'existe pas !");
						return true;
					}
				}
				else {
					player.sendMessage(ChatColor.RED + "Les arguments ne sont pas valides");
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
	        return "/ktp2135 setlobbyname [Arena name] [Name in lobby] - Set the name in lobby of the Arena.";
	      }
	      return "";
	}

	@Override
	public String getPermission() {
		return "KTP2135.edit";
	}
}
