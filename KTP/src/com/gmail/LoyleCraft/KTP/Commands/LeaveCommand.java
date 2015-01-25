package com.gmail.LoyleCraft.KTP.Commands;


import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.gmail.LoyleCraft.KTP.KTP;
import com.gmail.LoyleCraft.KTP.Arena.Arena;

public class LeaveCommand implements BasicCommand {

	private KTP plugin;
	  
	public LeaveCommand(KTP pl){
		this.plugin = pl;
	}

	@Override
	public boolean onCommand(Player player, String[] args) {
		if(KTP.hasPermission(player, getPermission()).booleanValue()) {
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
	    if (KTP.hasPermission(p, getPermission()).booleanValue()) {
	        return "/ktp leave - Leave an arena.";
	      }
	      return "";
	}

	@Override
	public String getPermission() {
		return "KTP.player";
	}
}
