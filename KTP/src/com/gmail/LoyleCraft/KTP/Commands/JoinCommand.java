package com.gmail.LoyleCraft.KTP.Commands;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.gmail.LoyleCraft.KTP.KTP;
import com.gmail.LoyleCraft.KTP.Arena.Arena;
@SuppressWarnings("unused")
public class JoinCommand implements BasicCommand {

	private KTP plugin;
	public static String pathKtp = "KTP2135.";
	  
	public JoinCommand(KTP pl){
		this.plugin = pl;
	}

	@Override
	public boolean onCommand(Player player, String[] args) {
		if(KTP.hasPermission(player, getPermission()).booleanValue()) {
			if(args.length != 1) {
				player.sendMessage(ChatColor.RED + "Il manque le nom de la map.");
			}
			else {	
				Arena arena = this.plugin.amanager.getArenaByName(args[0]);
				if(arena != null) {
					boolean canJoin = arena.getPlayerHandler().checkJoin(player);
					if(canJoin) {
						arena.getPlayerHandler().spawnPlayer(player);
						return true;
					}
				}
				else {
				player.sendMessage(ChatColor.RED + "Cette arène n'existe pas !");
				}
			}
		}
		return true;
	}

	@Override
	public String help(Player p) {
	    if (KTP.hasPermission(p, getPermission()).booleanValue()) {
	        return "/ktp join [Arena name] - Join an arena.";
	      }
	      return "";
	}

	@Override
	public String getPermission() {
		return "KTP.player";
	}
}