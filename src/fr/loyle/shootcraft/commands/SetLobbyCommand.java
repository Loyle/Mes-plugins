package fr.loyle.shootcraft.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import fr.loyle.shootcraft.ShootCraft;

public class SetLobbyCommand implements BasicCommand{
	
	private ShootCraft plugin;
	  
	public SetLobbyCommand(ShootCraft pl){
		this.plugin = pl;
	}

	@Override
	public boolean onCommand(Player player, String[] args) {
		if(ShootCraft.hasPermission(player, getPermission()).booleanValue()) {
			this.plugin.game.getGameManager().setLobby(player.getLocation());
			player.sendMessage(ChatColor.GREEN + "Lobby set !");
			return true;
		}
		return true;
	}

	@Override
	public String help(Player p) {
	    if (ShootCraft.hasPermission(p, getPermission()).booleanValue()) {
	        return "/shootcraft setlobby - Set le lobby.";
	      }
	      return "";
	}

	@Override
	public String getPermission() {
		return "ShootCraft.Admin";
	}
}
