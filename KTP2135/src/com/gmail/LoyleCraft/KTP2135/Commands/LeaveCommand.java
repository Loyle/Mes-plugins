package com.gmail.LoyleCraft.KTP2135.Commands;


import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.gmail.LoyleCraft.KTP2135.KTP2135;
import com.gmail.LoyleCraft.KTP2135.Arena.Arena;

public class LeaveCommand implements BasicCommand {

	private KTP2135 plugin;
	  
	public LeaveCommand(KTP2135 pl){
		this.plugin = pl;
	}

	@Override
	public boolean onCommand(Player player, String[] args) {
		if(KTP2135.hasPermission(player, getPermission()).booleanValue()) {
			Arena arena = this.plugin.amanager.getPlayerArena(player.getName());
			if(arena != null) {
				arena.getPlayerHandler().leavePlayer(player);
				return true;
			}
			else {
				player.sendMessage(ChatColor.RED + "Vous n'êtes pas dans une arène.");
			}
		}
		return true;
	}

	@Override
	public String help(Player p) {
	    if (KTP2135.hasPermission(p, getPermission()).booleanValue()) {
	        return "/ktp2135 leave - Leave an arena.";
	      }
	      return "";
	}

	@Override
	public String getPermission() {
		return "KTP2135.player";
	}
}
