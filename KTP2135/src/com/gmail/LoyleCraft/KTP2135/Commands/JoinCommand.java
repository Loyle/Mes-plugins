package com.gmail.LoyleCraft.KTP2135.Commands;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.gmail.LoyleCraft.KTP2135.KTP2135;
import com.gmail.LoyleCraft.KTP2135.Arena.Arena;

@SuppressWarnings("unused")
public class JoinCommand implements BasicCommand {

	private KTP2135 plugin;
	public static String pathKtp = "KTP2135.";
	  
	public JoinCommand(KTP2135 pl){
		this.plugin = pl;
	}

	@Override
	public boolean onCommand(Player player, String[] args) {
		if(KTP2135.hasPermission(player, getPermission()).booleanValue()) {
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
	    if (KTP2135.hasPermission(p, getPermission()).booleanValue()) {
	        return "/ktp2135 join [Arena name] - Join an arena.";
	      }
	      return "";
	}

	@Override
	public String getPermission() {
		return "KTP2135.player";
	}
}