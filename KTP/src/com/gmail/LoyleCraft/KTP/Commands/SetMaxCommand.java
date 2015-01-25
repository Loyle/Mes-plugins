package com.gmail.LoyleCraft.KTP.Commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.gmail.LoyleCraft.KTP.KTP;
import com.gmail.LoyleCraft.KTP.Arena.Arena;

public class SetMaxCommand implements BasicCommand {
	
	private KTP plugin;
	public SetMaxCommand(KTP pl){
		this.plugin = pl;
	}

	@Override
	public boolean onCommand(Player player, String[] args) {
		if(KTP.hasPermission(player, getPermission()).booleanValue()) {
			if(args.length != 2) {
				player.sendMessage(ChatColor.RED + "Il manque le nom de la map ou le nombre maximum");
				return true;
			}
			else {
				if(args[0] != null && args[1] != null ) {
					Arena arena = this.plugin.amanager.getArenaByName(args[0]);
					if(arena != null) {
						arena.getStructureManager().setMaxPlayers(Integer.parseInt(args[1]));
						player.sendMessage(ChatColor.YELLOW + "Le maximum de la map "+args[0]+" a bien été modifié !");
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
	    if (KTP.hasPermission(p, getPermission()).booleanValue()) {
	        return "/ktp setmax [Arena name] [number] - Set the maximum player in game.";
	      }
	      return "";
	}

	@Override
	public String getPermission() {
		return "KTP.edit";
	}

}
