package fr.loyle.shootcraft.game;

import java.io.File;
import java.io.IOException;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import fr.loyle.shootcraft.ShootCraft;

public class GameManager {
	private ShootCraft plugin;
	private String path = "ShootCraft.";
	private Boolean isStart = false;
	private Boolean isStarting = false;

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
		if (this.plugin.game.getPlayersManager().getNumberPlayers() >= this.getMinPlayers() && this.getIsStarting() == false && this.getIsStart() == false) {
			this.plugin.game.getGameHandler().runGameCountdown();
		}
		else if (this.plugin.game.getPlayersManager().getNumberPlayers() < this.getMinPlayers() && this.getIsStarting() == true && this.getIsStart() == false) {
			this.plugin.game.getGameHandler().stopGameCountdown();
		}
	}

	public void setIsStart(Boolean status) {
		this.isStart = status;
	}

	public boolean getIsStart() {
		return this.isStart;
	}

	public void setIsStarting(Boolean status) {
		this.isStarting = status;
	}

	public boolean getIsStarting() {
		return this.isStarting;
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

	public void addSpawn(Location l) {
		File spawnfile = new File("plugins/ShootCraft/Spawns.yml");
		FileConfiguration spawns = YamlConfiguration.loadConfiguration(spawnfile);
		int i = 1;
		while (spawns.isSet(""+i)) {
			i++;
		}
		spawns.set(i + ".X", l.getX());
		spawns.set(i + ".Y", l.getY());
		spawns.set(i + ".Z", l.getZ());
		spawns.set(i + ".Yaw", l.getYaw());
		spawns.set(i + ".Pitch", l.getPitch());
		spawns.set(i + ".World", l.getWorld().getName());
		
		
		try {
			spawns.save(new File("plugins/ShootCraft/Spawns.yml"));
		}
		catch (IOException exep) {
			exep.printStackTrace();

		}
	}
}
