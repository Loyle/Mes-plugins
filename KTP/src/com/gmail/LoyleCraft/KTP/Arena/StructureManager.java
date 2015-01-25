package com.gmail.LoyleCraft.KTP.Arena;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.gmail.LoyleCraft.KTP.KTP;

public class StructureManager {
	private Arena arena;
	private String world;
	public KTP plugin;
	private String w;
	private Double x;
	private Double y;
	private Double z;
	private Float yaw;
	private Float pitch;
	private boolean DamageEnabled = false;
	
	public StructureManager(Arena arena, KTP pl) {
		this.arena = arena;
		this.plugin = pl;
	}
	
	public String getWorldName() {
		return this.world;
	}
	
	public World getWorld() {
		return Bukkit.getWorld(this.world);
	}
	
	public Location getSpawnPoint() {
		FileConfiguration config = YamlConfiguration.loadConfiguration(this.arena.getArenaFile());
		World w = Bukkit.getWorld(config.getString("spawnpoint.w"));
		Double x = config.getDouble("spawnpoint.x");
		Double y = config.getDouble("spawnpoint.y");
		Double z = config.getDouble("spawnpoint.z");
		Float yaw = (float) config.getInt("spawnpoint.yaw");
		Float pitch = (float) config.getInt("spawnpoint.pitch");
		Location l = new Location(w, x, y, z, yaw, pitch);
		return l;
	}
	
	public boolean getDamageEnabled() {
		return this.DamageEnabled;
	}
	
	private int maxPlayers = 6;
	public int getMaxPlayers() {
		return this.maxPlayers;
	}
	
	private int minPlayers = 2;
	public int getMinPlayers() {
		return this.minPlayers;
	}
	
	private int countdown = 10;
	public int getCountDown() {
		return this.countdown;
	}
	
	public boolean setSpawnPoint(Location loc) {
		
		this.w = loc.getWorld().getName();
		this.x = loc.getX();
		this.y = loc.getY();
		this.z = loc.getZ();
		this.yaw = loc.getYaw();
		this.pitch = loc.getPitch();
		saveToConfig();
		loadFromConfig();
		return true;
	}
	
	public void setMaxPlayers(int maxplayers) {
		this.maxPlayers = maxplayers;
	}
	  
	public void setMinPlayers(int minplayers) {
		this.minPlayers = minplayers;
	}
	
	public void setCountdown(int countdown) {
		this.countdown = countdown;
	}
	
	public void setDamageEnabled(boolean value) {
		this.DamageEnabled = value;
	}
	
	public void saveToConfig() {
	    FileConfiguration config = new YamlConfiguration();
	    try {
	      config.set("world", this.world);
	    }
	    catch (Exception e) {}
	    if(this.w != null && this.x != null && this.y != null && this.z != null && this.yaw != null && this.pitch != null) {
	    	config.set("spawnpoint.w", this.w);
			config.set("spawnpoint.x", this.x);
			config.set("spawnpoint.y", this.y);
			config.set("spawnpoint.z", this.z);
			config.set("spawnpoint.yaw", this.yaw);
			config.set("spawnpoint.pitch", this.pitch);
	    }
	    
	    config.set("maxPlayers", Integer.valueOf(this.maxPlayers));
	    
	    config.set("minPlayers", Integer.valueOf(this.minPlayers));
	    
	    config.set("countdown", Integer.valueOf(this.countdown));
	    
	    try
	    {
	      config.save(this.arena.getArenaFile());
	    }
	    catch (IOException e)
	    {
	      e.printStackTrace();
	    }
	}
	  
	public void loadFromConfig() {
		FileConfiguration config = YamlConfiguration.loadConfiguration(this.arena.getArenaFile());
		
		this.w = config.getString("spawnpoint.w");
		this.x = config.getDouble("spawnpoint.x");
		this.y = config.getDouble("spawnpoint.y");
		this.z = config.getDouble("spawnpoint.z");
		this.yaw = (float)config.getInt("spawnpoint.yaw");
		this.pitch = (float)config.getInt("spawnpoint.pitch");
	    
		this.maxPlayers = config.getInt("maxPlayers", this.maxPlayers);
	    
		this.minPlayers = config.getInt("minPlayers", this.minPlayers);
	
		this.countdown = config.getInt("countdown", this.countdown);
	    
	}
}
