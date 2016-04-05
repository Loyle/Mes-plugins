package fr.loyle.shootcraft.listener;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import fr.loyle.shootcraft.ShootCraft;
import fr.loyle.shootcraft.game.RechargeManager;

public class PlayerListener implements Listener {

	private ShootCraft plugin;

	public PlayerListener(ShootCraft pl) {
		this.plugin = pl;
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player player = e.getPlayer();
		if (this.plugin.game.getGameManager().getIsStart()) {
			this.plugin.game.getPlayersManager().addSpectator(player);
			e.setJoinMessage(ChatColor.GRAY + player.getName() + " regarde la partie");
		}
		else {
			this.plugin.game.getPlayersManager().addPlayer(player);

			e.setJoinMessage(ChatColor.YELLOW + player.getName() + " a rejoint (" + this.plugin.game.getPlayersManager().getNumberPlayers() + "/" + this.plugin.game.getGameManager().getMaxPlayers() + ")");
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerLeave(PlayerQuitEvent e) {
		Player player = e.getPlayer();
		if (this.plugin.game.getPlayersManager().checkPlayer(player)) {
			this.plugin.game.getPlayersManager().removePlayer(player);
			e.setQuitMessage(ChatColor.YELLOW + player.getName() + " a quitter (" + this.plugin.game.getPlayersManager().getNumberPlayers() + "/" + this.plugin.game.getGameManager().getMaxPlayers() + ")");
		}
		else {
			this.plugin.game.getPlayersManager().removeSpectator(player);
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onFoodLevelChange(FoodLevelChangeEvent e) {
		e.setCancelled(true);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onDropItem(PlayerDropItemEvent e) {
		if (this.plugin.game.getGameManager().getIsStart()) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerDamage(EntityDamageEvent e) {
		if(this.plugin.game.getGameManager().getIsStart()) {
			if(e.getEntity() instanceof Player) {
				e.setCancelled(true);
			}
		}
	}

	/*
	 * 
	 * Interact event for the hoe
	 */

	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.HIGHEST)
	void onPlayerInteract(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		ItemStack hoe = player.getItemInHand();
		if (player.getLevel() == 1) {
			if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
				if (hoe != null && hoe.getType().equals(Material.WOOD_HOE) && hoe.getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Houe en bois")) {
					e.setCancelled(true);

					Player targetPlayer = this.plugin.game.getPlayersManager().shootPlayer(player);
					player.playSound(player.getLocation(), Sound.BLOCK_DISPENSER_LAUNCH, 70, 1);
					RechargeManager recharge = new RechargeManager(this.plugin, player);
					recharge.recharge(1.6);

					if (targetPlayer != null && this.plugin.game.getPlayersManager().checkPlayer(targetPlayer)) {
						Bukkit.broadcastMessage(ChatColor.RED + player.getName() + ChatColor.WHITE + " a tué " + ChatColor.RED + targetPlayer.getName());
						player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 50, 1);

						this.plugin.game.getScoreManager().addScore(player, 1);

						targetPlayer.setLevel(1);
						targetPlayer.setExp(1);
						targetPlayer.teleport(this.plugin.game.getPlayersManager().getRandomSpawn());
					}
				}
			}
		}
		else {
			player.playSound(player.getLocation(), Sound.BLOCK_DISPENSER_FAIL, 70, 1);
		}
	}
}
