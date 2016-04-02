package fr.loyle.shootcraft.listener;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
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

import fr.loyle.shootcraft.ShootCraft;
import fr.loyle.shootcraft.libraries.NmsUtils;

public class PlayerListener implements Listener {

	private ShootCraft plugin;

	public PlayerListener(ShootCraft pl) {
		this.plugin = pl;
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player player = e.getPlayer();
		this.plugin.game.getPlayersManager().addPlayer(player);

		player.getInventory().clear();
		player.setGameMode(GameMode.ADVENTURE);

		NmsUtils.sendTitle(player, ChatColor.GOLD + "ShootCraft", ChatColor.RED + "Bienvenue dans le mini-jeu ShootCraft", 0, 80, 10);
		e.setJoinMessage(ChatColor.YELLOW + player.getName() + " a rejoint (" + this.plugin.game.getPlayersManager().getNumberPlayers() + "/" + this.plugin.game.getGameManager().getMaxPlayers() + ")");
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerLeave(PlayerQuitEvent e) {
		Player player = e.getPlayer();
		this.plugin.game.getPlayersManager().removePlayer(player);
		e.setQuitMessage(ChatColor.YELLOW + player.getName() + " a quitter (" + this.plugin.game.getPlayersManager().getNumberPlayers() + "/" + this.plugin.game.getGameManager().getMaxPlayers() + ")");
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

	/*
	 * 
	 * Interact event for the hoe
	 */

	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.HIGHEST)
	void onPlayerInteract(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		ItemStack hoe = player.getItemInHand();

		try {
			if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
				if (hoe != null && hoe.getType().equals(Material.WOOD_HOE) && hoe.getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Houe en bois")) {
					e.setCancelled(true);

					Player targetPlayer = this.plugin.game.getPlayersManager().shootPlayer(player);

					if (targetPlayer != null) {
						Bukkit.broadcastMessage(ChatColor.RED + player.getName() + ChatColor.WHITE + " a tué " + ChatColor.RED + targetPlayer.getName());

						this.plugin.game.getScoreManager().addScore(player, 1);

						targetPlayer.teleport(this.plugin.game.getPlayersManager().getRandomSpawn());
					}
				}
			}
		}
		catch (Exception exception) {
			System.out.println(exception);
		}
	}
}
