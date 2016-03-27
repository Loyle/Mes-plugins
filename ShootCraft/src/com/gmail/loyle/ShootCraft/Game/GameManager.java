package com.gmail.loyle.shootcraft.game;

import org.bukkit.Location;

import com.gmail.loyle.shootcraft.ShootCraft;

public class GameManager {
	public ShootCraft plugin;
	private String path = "ShootCraft.";
	private Boolean isStart = false;
	
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
			this.plugin.game.GameHandler.runGameCountdown();		}
		else {
			this.plugin.game.GameHandler.stopGameCountdown();
		}
	}
	
	public void setIsStart(Boolean status) {
		this.isStart = status;
	}
	public boolean getIsStart() {
		return this.isStart;
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
