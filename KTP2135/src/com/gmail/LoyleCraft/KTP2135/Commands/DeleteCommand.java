package com.gmail.LoyleCraft.KTP2135.Commands;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.gmail.LoyleCraft.KTP2135.KTP2135;
import com.gmail.LoyleCraft.KTP2135.Arena.Arena;

public class DeleteCommand implements BasicCommand {

	private KTP2135 plugin;
	  
	public DeleteCommand(KTP2135 pl){
		this.plugin = pl;
	}

	@Override
	public boolean onCommand(Player player, String[] args) {
		if(KTP2135.hasPermission(player, getPermission()).booleanValue()) {
			if(args.length != 1) {
				player.sendMessage(ChatColor.RED + "Il manque le nom de la map !");
				return true;
			}
			else {
				Arena arena = this.plugin.amanager.getArenaByName(args[0]);
				if(arena == null) {
					player.sendMessage(ChatColor.RED + "Cette map existe pas.");
					return true;
				}
				new File(this.plugin.getDataFolder() + File.separator + "arenas" + File.separator + arena.getArenaName() + ".yml").delete();
				this.plugin.amanager.unregisterArena(arena);
				player.sendMessage(ChatColor.YELLOW + "Arena supprimé avec succès");
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
	    if (KTP2135.hasPermission(p, getPermission()).booleanValue()) {
	        return "/ktp2135 delete [Arena name] - Delete an arena.";
	      }
	      return "";
	}

	@Override
	public String getPermission() {
		return "KTP2135.edit";
	}
}