package com.gmail.loyle.shootcraft.listener;

import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import com.gmail.loyle.shootcraft.ShootCraft;
import com.gmail.loyle.shootcraft.libraries.NmsUtils;

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
		e.setJoinMessage(ChatColor.YELLOW + player.getName() + " a rejoint (" + this.plugin.game.PlayersManager.getNumberPlayers() + "/" + this.plugin.game.GameManager.getMaxPlayers() + ")");
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerLeave(PlayerQuitEvent e) {
		Player player = e.getPlayer();
		this.plugin.game.PlayersManager.removePlayer(player);
		e.setQuitMessage(ChatColor.YELLOW + player.getName() + " a quitter (" + this.plugin.game.PlayersManager.getNumberPlayers() + "/" + this.plugin.game.GameManager.getMaxPlayers() + ")");
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onFoodLevelChange(FoodLevelChangeEvent e) {
		e.setCancelled(true);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onDropItem(PlayerDropItemEvent e) {
		if (this.plugin.game.GameManager.getIsStart()) {
			e.setCancelled(true);
		}
	}

	/*
	 * Firework and gun function !!!!!
	 */
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerInteract(PlayerInteractEvent e) {
		if(this.plugin.game.GameManager.getIsStart()) {
			Action action = e.getAction();
			Player player = e.getPlayer();
			ItemStack hoe = e.getItem();
			
			if(action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK && hoe.getType().equals(Material.WOOD_HOE) && hoe.getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Houe en bois")) {
				Location l = player.getLocation();
				// En cours de travail
			}
		}
	}
}
