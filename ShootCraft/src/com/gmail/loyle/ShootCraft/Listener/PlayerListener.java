package com.gmail.loyle.ShootCraft.Listener;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.gmail.loyle.ShootCraft.ShootCraft;
import com.gmail.loyle.ShootCraft.Libraries.NmsUtils;

public class PlayerListener implements Listener {
	
	public ShootCraft plugin;
	public PlayerListener(ShootCraft pl) {
		this.plugin = pl;
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player player = e.getPlayer();
		this.plugin.game.PlayersManager.addPlayer(player);
		
		player.getInventory().clear();
		player.setGameMode(GameMode.ADVENTURE);
		
		NmsUtils.sendTitle(player, ChatColor.GOLD + "ShootCraft", ChatColor.RED + "Bienvenue dans le mini-jeu ShootCraft", 0, 80, 10);
		e.setJoinMessage(ChatColor.YELLOW + player.getName() + " a rejoint ("+this.plugin.game.PlayersManager.getNumberPlayers()+"/"+this.plugin.game.GameManager.getMaxPlayers()+")");
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerLeave(PlayerQuitEvent e) {
		Player player = e.getPlayer();
		this.plugin.game.PlayersManager.removePlayer(player);
		e.setQuitMessage(ChatColor.YELLOW + player.getName() + " a quitter ("+this.plugin.game.PlayersManager.getNumberPlayers()+"/"+this.plugin.game.GameManager.getMaxPlayers()+")");
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onFoodLevelChange(FoodLevelChangeEvent e) {
		e.setCancelled(true);
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onDropItem(PlayerDropItemEvent e) {
		if(this.plugin.game.GameManager.getIsStart()) {
			e.setCancelled(true);
		}
	}
}
