package com.gmail.loyle.shootcraft.game;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import com.gmail.loyle.shootcraft.ShootCraft;
import com.gmail.loyle.shootcraft.libraries.NmsUtils;

public class GameHandler {
	public ShootCraft plugin;
	private String path = "ShootCraft.";
	private int runtaskid;
	private int count;

	public GameHandler(ShootCraft pl) {
		this.plugin = pl;
	}

	/*
	 * Run countdown before start the game
	 */

	public void runGameCountdown() {
		this.count = GameHandler.this.plugin.getConfig().getInt(path + "CountDown");

		this.runtaskid = Bukkit.getScheduler().scheduleSyncRepeatingTask(this.plugin, new Runnable() {
			public void run() {
				if (GameHandler.this.count <= -1) {
					GameHandler.this.stopGameCountdown();
					GameHandler.this.startGame();
				}
				else {
					for (Player player : GameHandler.this.plugin.game.PlayersManager.getPlayers()) {
						if (GameHandler.this.count == 60 || GameHandler.this.count == 30 || GameHandler.this.count == 10 || GameHandler.this.count <= 5) {
							NmsUtils.sendTitle(player, ChatColor.GOLD + "Démarrage dans " + GameHandler.this.count, "", 5, 10, 5);
							player.playSound(player.getLocation(), Sound.BLOCK_DISPENSER_DISPENSE, 20, 1);
						}

						player.setLevel(GameHandler.this.count);
					}
					GameHandler.this.count--;
				}
			}
		}, 0L, 20L);
	}

	/*
	 * Stop countdown
	 */
	public void stopGameCountdown() {
		Bukkit.getScheduler().cancelTask(this.runtaskid);
	}
	
	/*
	 * Start the game !
	 */
	public void startGame() {
		this.plugin.game.GameManager.setIsStart(true);
		this.plugin.game.ScoreboardManager.reloadScoreboard();
		
		
		for (Player player : this.plugin.game.PlayersManager.getPlayers()) {
			player.setFoodLevel(2000);
			player.setHealth(20.0);
			this.plugin.game.PlayersManager.initInventory(player);
			player.setGameMode(GameMode.ADVENTURE);
			
			for (PotionEffect effect : player.getActivePotionEffects()) {
				player.removePotionEffect(effect.getType());
			}

			NmsUtils.sendTitle(player, ChatColor.GOLD + "C'est parti !", "", 5, 20, 5);

			this.runtaskid = Bukkit.getScheduler().scheduleSyncRepeatingTask(this.plugin, new Runnable() {
				public void run() {
					GameHandler.this.plugin.game.ScoreboardManager.reloadScoreboard();
				}
			}, 0L, 20L);
		}
	}
}
