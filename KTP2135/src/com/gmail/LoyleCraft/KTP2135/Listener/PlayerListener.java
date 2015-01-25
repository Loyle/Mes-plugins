package com.gmail.LoyleCraft.KTP2135.Listener;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

/*import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;*/
import com.gmail.LoyleCraft.KTP2135.KTP2135;
import com.gmail.LoyleCraft.KTP2135.Arena.Arena;

public class PlayerListener implements Listener {
	
	public KTP2135 plugin;
	public static String pathKtp = "KTP2135.";
	
	public PlayerListener(KTP2135 pl){
		this.plugin = pl;
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerDisconnect(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		Arena arena = this.plugin.amanager.getPlayerArena(p.getName());
		if(arena != null) {
			arena.getPlayerHandler().leavePlayer(p);
			return;
		}
		return;
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerRespawn(PlayerRespawnEvent e) {
		Player p = e.getPlayer();
		Arena arena = this.plugin.amanager.getPlayerArena(p.getName());
		if(arena != null) {
			p.sendMessage(ChatColor.YELLOW + "Dommage vous êtes mort.");
			arena.getPlayerHandler().leavePlayer(p);
			Location l = this.plugin.amanager.getLobbySpawn();
			e.setRespawnLocation(l);
			return;
			
		}
		return;
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
			e.setCancelled(true);
			return;
		}
		return;
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerBreakBlock(BlockBreakEvent e) {
		Player player = e.getPlayer();
		Arena arena = this.plugin.amanager.getPlayerArena(player.getName());
		if(arena != null) {
			e.setCancelled(true);
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
