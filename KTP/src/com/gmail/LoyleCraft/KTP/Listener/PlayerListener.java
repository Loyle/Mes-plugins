package com.gmail.LoyleCraft.KTP.Listener;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import com.gmail.LoyleCraft.KTP.KTP;
import com.gmail.LoyleCraft.KTP.Arena.Arena;


public class PlayerListener implements Listener {
	
	public KTP plugin;
	public static String pathKtp = "KTP2135.";
	
	public PlayerListener(KTP pl){
		this.plugin = pl;
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerDisconnect(PlayerQuitEvent e) {
		Player player = e.getPlayer();
		Arena arena = this.plugin.amanager.getPlayerArena(player.getName());
		if(arena != null) {
			arena.getPlayerHandler().leavePlayer(player);
		    for (Player otherp : arena.getPlayersManager().getAllParticipantsCopy()) {
		    	otherp.playSound(otherp.getLocation(), Sound.WITHER_SPAWN, 20, 1);
		    }
			return;
		}
		return;
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerRespawn(PlayerRespawnEvent e) {
		Player player = e.getPlayer();
		Arena arena = this.plugin.amanager.getPlayerArena(player.getName());
		if(arena != null) {
			arena.getPlayerHandler().leavePlayer(player);
			Location respawn = this.plugin.amanager.getLobbySpawn();
			e.setRespawnLocation(respawn);
		}
	}
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerDeath(PlayerDeathEvent e) {
		if(e.getEntity() instanceof Player) {
			Player player = (Player)e.getEntity();
			Arena arena = this.plugin.amanager.getPlayerArena(player.getName());
		    if(arena != null) {
			    for (Player otherp : arena.getPlayersManager().getAllParticipantsCopy()) {
			        otherp.playSound(player.getLocation(), Sound.WITHER_SPAWN, 20, 1);
			    }
		    	player.sendMessage(ChatColor.YELLOW + "Dommage vous êtes mort !");
		    }
		}
	}
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerDamage(EntityDamageEvent e) {
		if(e.getEntity() instanceof Player) {
			Player player = (Player)e.getEntity();
		    Arena arena = this.plugin.amanager.getPlayerArena(player.getName());
		    if(arena != null) {
		    	if(arena.getStructureManager().getDamageEnabled() == false) {
		    		e.setCancelled(true);
		    		return;
		    	}
		    	return;
		    }
		    return;
		}
		return;
	}
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerPlaceBlock(BlockPlaceEvent e) {
		
		Player player = e.getPlayer();
		Arena arena = this.plugin.amanager.getPlayerArena(player.getName());
		if(arena != null) {
			if(arena.getStatusManager().isArenaRunning() == false) {
				e.setCancelled(true);
				return;
			}
			return;
		}
		return;
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerBreakBlock(BlockBreakEvent e) {
		Player player = e.getPlayer();
		Arena arena = this.plugin.amanager.getPlayerArena(player.getName());
		if(arena != null) {
			if(arena.getStatusManager().isArenaRunning() == false) {
				e.setCancelled(true);
				return;
			}
			return;
		}
		return;
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerSendMessage(PlayerCommandPreprocessEvent e) {
		Player player = e.getPlayer();
		String arg = e.getMessage();
		String[] args = arg.split(" ");
		Arena arena = this.plugin.amanager.getPlayerArena(player.getName());
		if(arena != null) {
			if(args[0].equalsIgnoreCase("/spawn") || args[0].equalsIgnoreCase("/warp")) {
				player.sendMessage(ChatColor.RED+"Vous ne pouvez pas faire cette commande en jeux.");
				e.setCancelled(true);
				return;
			}
		}
	}
}
