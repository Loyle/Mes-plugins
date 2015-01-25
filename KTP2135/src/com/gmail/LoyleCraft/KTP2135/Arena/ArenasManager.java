package com.gmail.LoyleCraft.KTP2135.Arena;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import com.gmail.LoyleCraft.KTP2135.KTP2135;

public class ArenasManager {
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private HashMap<String, Arena> arenanames = new HashMap();
	public KTP2135 plugin;
	private String pathKtp = "KTP2135.";
	
	public ArenasManager(KTP2135 pl) {
		this.plugin = pl;
	}
	  
	public void registerArena(Arena arena) {
		this.arenanames.put(arena.getArenaName(), arena);
	}
	  
	public void unregisterArena(Arena arena) {
		this.arenanames.remove(arena.getArenaName());
	}
	  
	public Collection<Arena> getArenas() {
		return this.arenanames.values();
	}
	  
	public Set<String> getArenasNames() {
		return this.arenanames.keySet();
	}
	  
	public Arena getArenaByName(String name) {
		return (Arena)this.arenanames.get(name);
	}
	  
	public Arena getPlayerArena(String name) {
		for (Arena arena : this.arenanames.values()) {
				if (arena.getPlayersManager().isInArena(name)) {
				return arena;
				}
		}
		return null;
	}
	public void setLobbySpawn(Location loc) {
		this.plugin.getConfig().set(pathKtp+"LobbySpawn.world", loc.getWorld().getName());
		this.plugin.getConfig().set(pathKtp+"LobbySpawn.x", loc.getX());
		this.plugin.getConfig().set(pathKtp+"LobbySpawn.y", loc.getY());
		this.plugin.getConfig().set(pathKtp+"LobbySpawn.z", loc.getZ());
		this.plugin.getConfig().set(pathKtp+"LobbySpawn.yaw", loc.getYaw());
		this.plugin.getConfig().set(pathKtp+"LobbySpawn.pitch", loc.getPitch());
		plugin.saveConfig();
	}
	public Location getLobbySpawn() {
		
		World w = Bukkit.getWorld(this.plugin.getConfig().getString(pathKtp+"LobbySpawn.world"));
		Double x = this.plugin.getConfig().getDouble(pathKtp+"LobbySpawn.x");
		Double y = this.plugin.getConfig().getDouble(pathKtp+"LobbySpawn.y");
		Double z = this.plugin.getConfig().getDouble(pathKtp+"LobbySpawn.z");
		Float yaw = (float) this.plugin.getConfig().getInt(pathKtp+"LobbySpawn.yaw");
		Float pitch = (float) this.plugin.getConfig().getInt(pathKtp+"LobbySpawn.pitch");
		Location l = new Location(w, x, y, z, yaw, pitch);
		return l;
	}
}
