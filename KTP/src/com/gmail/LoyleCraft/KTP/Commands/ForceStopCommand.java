package com.gmail.LoyleCraft.KTP.Commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.gmail.LoyleCraft.KTP.KTP;
import com.gmail.LoyleCraft.KTP.Arena.Arena;

public class ForceStopCommand implements BasicCommand {
	
	private KTP plugin;
	public ForceStopCommand(KTP pl){
		this.plugin = pl;
	}

	@Override
	public boolean onCommand(Player player, String[] args) {
		if(KTP.hasPermission(player, getPermission()).booleanValue()) {
			if(args.length != 1) {
				player.sendMessage(ChatColor.RED + "Il manque le nom de la map.");
				return true;
			}
			else {
				if(args[0] != null) {
					Arena arena = this.plugin.amanager.getArenaByName(args[0]);
					if(arena != null) {
						if(arena.getStatusManager().isArenaStarting() == true || arena.getStatusManager().isArenaRunning() == true) {
							arena.getGameHandler().stopArena();
							player.sendMessage(ChatColor.GREEN+"La map "+arena.getArenaName()+" a bien été arrété");
							return true;
						}
						else {
							player.sendMessage(ChatColor.RED+"La map "+arena.getArenaName()+" n'est pas en cours de fonctionnement");
							return true;
						}
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
	        return "/ktp stop [Arena name] - Force arena stop.";
	      }
	      return "";
	}

	@Override
	public String getPermission() {
		return "KTP.edit";
	}
}
