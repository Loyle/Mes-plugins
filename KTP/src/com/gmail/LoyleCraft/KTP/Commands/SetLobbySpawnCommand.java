package com.gmail.LoyleCraft.KTP.Commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.gmail.LoyleCraft.KTP.KTP;

public class SetLobbySpawnCommand implements BasicCommand {
	
	private KTP plugin;
	public SetLobbySpawnCommand(KTP pl){
		this.plugin = pl;
	}

	@Override
	public boolean onCommand(Player player, String[] args) {
		if(KTP.hasPermission(player, getPermission()).booleanValue()) {
			Location l = player.getLocation();
			this.plugin.amanager.setLobbySpawn(l);
			player.sendMessage(ChatColor.YELLOW + "le spawn du lobby a bien été modifié !");
			return true;
		}
		else {
			player.sendMessage(ChatColor.RED + "Vous n'avez pas la permission !");
			return true;
		}
	}

	@Override
	public String help(Player p) {
	    if (KTP.hasPermission(p, getPermission()).booleanValue()) {
	        return "/ktp setlobbyspawn - Set the spawn of lobby.";
	      }
	      return "";
	}

	@Override
	public String getPermission() {
		return "KTP.edit";
	}
}