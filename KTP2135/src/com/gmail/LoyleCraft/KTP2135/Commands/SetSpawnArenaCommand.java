package com.gmail.LoyleCraft.KTP2135.Commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.gmail.LoyleCraft.KTP2135.KTP2135;
import com.gmail.LoyleCraft.KTP2135.Arena.Arena;

public class SetSpawnArenaCommand implements BasicCommand {

	private KTP2135 plugin;
	  
	public SetSpawnArenaCommand(KTP2135 pl){
		this.plugin = pl;
	}

	@Override
	public boolean onCommand(Player player, String[] args) {
		if(KTP2135.hasPermission(player, getPermission()).booleanValue()) {
			if(args.length != 1) {
				player.sendMessage(ChatColor.RED + "Merci d'indiquer le nom de votre map.");
				return true;
			}
			else {
				Arena arena = this.plugin.amanager.getArenaByName(args[0]);
				if(arena != null) {
					arena.getStructureManager().setSpawnPoint(player.getLocation());
					player.sendMessage(ChatColor.YELLOW + "Spawn set avec succès.");
					return true;
				}
				else {
					player.sendMessage(ChatColor.RED + "Cette map n'existe pas.");
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
	        return "/ktp2135 setspawnarena [Arena name] - Set spawn of arena.";
	      }
	      return "";
	}

	@Override
	public String getPermission() {
		return "KTP2135.edit";
	}
}
