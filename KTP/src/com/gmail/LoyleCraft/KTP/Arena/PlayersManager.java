package com.gmail.LoyleCraft.KTP.Arena;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import org.bukkit.entity.Player;

public class PlayersManager {
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private HashMap<String, Player> players = new HashMap();
	  @SuppressWarnings({ "unchecked", "rawtypes" })
	private HashMap<String, Player> spectators = new HashMap();
	  
	  public boolean isInArena(String name) {
		  return (this.players.containsKey(name)) || (this.spectators.containsKey(name));
	  }
	  
	  @SuppressWarnings({ "unchecked", "rawtypes" })
	  public HashSet<Player> getAllParticipantsCopy() {
	    HashSet<Player> p = new HashSet();
	    p.addAll(this.players.values());
	    p.addAll(this.spectators.values());
	    return p;
	  }
	  
	  public Collection<Player> getPlayers(){
	    return Collections.unmodifiableCollection(this.players.values());
	  }
	  
	  @SuppressWarnings({ "rawtypes", "unchecked" })
	  public HashSet<Player> getPlayersCopy(){
	    return new HashSet(this.players.values());
	  }
	  
	  public int getCount() {
		  return this.players.size();
	  }
	  
	  public void addPlayer(Player player) {
		  this.players.put(player.getName(), player);
	  }
	  
	  public void removePlayer(Player player) {
		  this.players.remove(player.getName());
	  }
	  
	  public boolean isSpectator(String name) {
		  return this.spectators.containsKey(name);
	  }
	  
	  public void addSpectator(Player player) {
		  this.spectators.put(player.getName(), player);
	  }
	  
	  public void removeSpectator(Player player) {
		  this.spectators.remove(player.getName());
	  }
}
