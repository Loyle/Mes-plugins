package com.gmail.LoyleCraft.KTP2135.Arena;

import java.io.File;

import com.gmail.LoyleCraft.KTP2135.KTP2135;

public class Arena {

	public KTP2135 plugin;
	private String arenaname;
	private File arenafile;
	private PlayerHandler arenaph;
	private GameHandler arenagh;
	
	public Arena(KTP2135 pl, String name) {
		this.arenaname = name;
	    this.plugin = pl;
	    this.arenagh = new GameHandler(plugin, this);
	    this.arenaph = new PlayerHandler(plugin, this);
	    this.arenafile = new File(plugin.getDataFolder() + File.separator + "arenas" + File.separator + this.arenaname + ".yml");
	}
	
	public File getArenaFile() {
		return this.arenafile;
	}
	
	public String getArenaName() {
		return this.arenaname;
	}
	
	public GameHandler getGameHandler() {
		return this.arenagh;
	}
	
	private StatusManager statusManager = new StatusManager(this);
	public StatusManager getStatusManager() {
		return this.statusManager;
	}
	
	private StructureManager StructureManager = new StructureManager(this,this.plugin);
	public StructureManager getStructureManager() {
		return this.StructureManager;
	}
	
	private PlayersManager playersManager = new PlayersManager(); 
	public PlayersManager getPlayersManager() {
		return this.playersManager;
	}
	
	public PlayerHandler getPlayerHandler() {
		return this.arenaph;
	}
}
