package com.gmail.loyle.ShootCraft.Game;

import org.bukkit.Location;

import com.gmail.loyle.ShootCraft.ShootCraft;

public class GameManager {
	public ShootCraft plugin;
	private String path = "ShootCraft.";
	
	public GameManager(ShootCraft pl) {
		this.plugin = pl;
	}
	
	public int getMinPlayers() {
		return this.plugin.getConfig().getInt(path + "MinPlayers");
	}
	public int getMaxPlayers() {
		return this.plugin.getConfig().getInt(path + "MaxPlayers");
	}
	public void checkStart() {
		if(this.plugin.game.PlayersManager.getNumberPlayers() >= this.getMinPlayers()) {
			this.plugin.getServer().broadcastMessage("START");
		}
		else {
			this.plugin.getServer().broadcastMessage("STOP");
		}
	}
	
	public void setLobby(Location l) {
		this.plugin.getConfig().set(path + "Lobby.X", l.getX());
		this.plugin.getConfig().set(path + "Lobby.Y", l.getY());
		this.plugin.getConfig().set(path + "Lobby.Z", l.getZ());
		this.plugin.getConfig().set(path + "Lobby.Yaw", l.getYaw());
		this.plugin.getConfig().set(path + "Lobby.Pitch", l.getPitch());
		this.plugin.getConfig().set(path + "Lobby.World", l.getWorld().getName());
		
		this.plugin.saveConfig();
	}
}
