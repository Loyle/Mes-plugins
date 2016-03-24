package com.gmail.loyle.ShootCraft.Commands;

import org.bukkit.entity.Player;

import com.gmail.loyle.ShootCraft.ShootCraft;

public class ExempleCommand implements BasicCommand{
	
	private ShootCraft plugin;
	  
	public ExempleCommand(ShootCraft pl){
		this.plugin = pl;
	}

	@Override
	public boolean onCommand(Player player, String[] args) {
		if(ShootCraft.hasPermission(player, getPermission()).booleanValue()) {
			this.plugin.getServer().broadcastMessage("Exemple broadcast.");
			player.sendMessage("Exemple message to player.");
			return true;
		}
		return true;
	}

	@Override
	public String help(Player p) {
	    if (ShootCraft.hasPermission(p, getPermission()).booleanValue()) {
	        return "/ktp2135 exemple - Exemple of command.";
	      }
	      return "";
	}

	@Override
	public String getPermission() {
		return "BaseProject.player";
	}
}
