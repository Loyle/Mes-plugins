package com.gmail.LoyleCraft.KTP2135.Commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import com.gmail.LoyleCraft.KTP2135.KTP2135;

public class SetLobbySpawnCommand implements BasicCommand {
	
	private KTP2135 plugin;
	public SetLobbySpawnCommand(KTP2135 pl){
		this.plugin = pl;
	}

	@Override
	public boolean onCommand(Player player, String[] args) {
		if(KTP2135.hasPermission(player, getPermission()).booleanValue()) {
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
	    if (KTP2135.hasPermission(p, getPermission()).booleanValue()) {
	        return "/ktp2135 setlobbyspawn - Set the spawn of lobby.";
	      }
	      return "";
	}

	@Override
	public String getPermission() {
		return "KTP2135.edit";
	}
}