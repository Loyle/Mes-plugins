package com.gmail.LoyleCraft.KTP.Commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.gmail.LoyleCraft.KTP.KTP;
import com.gmail.LoyleCraft.KTP.Arena.Arena;

public class CreateCommand implements BasicCommand {

	private KTP plugin;
	  
	public CreateCommand(KTP pl){
		this.plugin = pl;
	}

	@Override
	public boolean onCommand(Player player, String[] args) {
		if(KTP.hasPermission(player, getPermission()).booleanValue()) {
			if(args.length != 1) {
				player.sendMessage(ChatColor.RED + "Il manque le nom de la map !");
				return true;
			}
			else {
				Arena arenac = this.plugin.amanager.getArenaByName(args[0]);
				if(arenac != null) {
					player.sendMessage(ChatColor.RED + "Cette map existe déjà.");
					return true;
				}
				Arena arena = new Arena(this.plugin, args[0]);
				this.plugin.amanager.registerArena(arena);
				player.sendMessage(ChatColor.YELLOW + "Arena crée avec succès");
				arena.getStructureManager().saveToConfig();
				arena.getStructureManager().loadFromConfig();
				return true;
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
	        return "/ktp create [Arena name] - Create an arena.";
	      }
	      return "";
	}

	@Override
	public String getPermission() {
		return "KTP.edit";
	}
}
