package com.gmail.loyle.ShootCraft.Game;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import com.gmail.loyle.ShootCraft.ShootCraft;

public class PlayersManager {
	public ShootCraft plugin;
	
	private HashMap<UUID, Player> players = new HashMap<>();
	@SuppressWarnings("unused")
	private HashMap<UUID, Player> spectators = new HashMap<>();
	private String path = "ShootCraft.";
	
	public PlayersManager(ShootCraft pl) {
		this.plugin = pl;
	}
	
	public void addPlayer(Player player) {
		if(this.plugin.getConfig().isSet(path + "Lobby")) {
			Double x = this.plugin.getConfig().getDouble(path + "Lobby.X");
			Double y = this.plugin.getConfig().getDouble(path + "Lobby.Y");
			Double z = this.plugin.getConfig().getDouble(path + "Lobby.Z");
			Float yaw = (float) this.plugin.getConfig().getDouble(path + "Lobby.Yaw");
			Float pitch = (float) this.plugin.getConfig().getDouble(path + "Lobby.Pitch");
			World world = this.plugin.getServer().getWorld(this.plugin.getConfig().getString(path + "Lobby.World"));
			
			Location l = new Location(world, x, y, z, yaw, pitch);
			player.teleport(l);
		}
		else {
			player.sendMessage(ChatColor.RED + "Lobby non définit");
		}
		
		
		this.players.put(player.getUniqueId(), player);
		this.plugin.game.GameManager.checkStart();
	}
	public void removePlayer(Player player) {
		this.players.remove(player.getUniqueId());
		this.plugin.game.GameManager.checkStart();
	}
	
	public int getNumberPlayers() {
		return this.players.size();
	}
	
	public Collection<Player> getPlayers() {
		return Collections.unmodifiableCollection(this.players.values());
	}
	
	public boolean checkPlayer(Player player) {
		return this.players.containsKey(player.getUniqueId());
	}
}
