package com.gmail.LoyleCraft.KTP.Arena;

public class StatusManager {
	@SuppressWarnings("unused")
	private Arena arena;
	
	public StatusManager(Arena arena){
		this.arena = arena;
	}
	private boolean enabled = false;
	private boolean starting = false;
	private boolean running = false;
	private boolean regenerating = false;
	
	public boolean isArenaEnabled() {
		return this.enabled;
	}
	
	public boolean enableArena() {
		this.enabled = true;
		return true;
	}
	
	public boolean disableArena() {
		this.enabled = false;
		return true;
	}
	
	public boolean isArenaStarting() {
		return this.starting;
	}
	
	public void setStarting(boolean starting) {
		this.starting = starting;
	}
	
	public boolean isArenaRunning() {
		return this.running;
	}
	  
	public void setRunning(boolean running) {
		this.running = running;
	}
	  
	public boolean isArenaRegenerating(){
		return this.regenerating;
	}
	  
	public void setRegenerating(boolean regenerating) {
		this.regenerating = regenerating;
	}
}
