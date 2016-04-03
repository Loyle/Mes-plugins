package fr.loyle.shootcraft.game;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import fr.loyle.shootcraft.ShootCraft;
import fr.loyle.shootcraft.libraries.NmsUtils;

public class GameHandler {
	private ShootCraft plugin;
	private String path = "ShootCraft.";
	private int runtaskid;
	private int count;
	private int finishcount = 10;

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
				GameHandler.this.plugin.game.getGameManager().setIsStarting(true);
				if (GameHandler.this.count <= -1) {
					GameHandler.this.stopGameCountdown();
					GameHandler.this.startGame();
				}
				else {
					for (Player player : GameHandler.this.plugin.game.getPlayersManager().getPlayers()) {
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
		GameHandler.this.plugin.game.getGameManager().setIsStarting(false);
	}
	
	/*
	 * Start the game !
	 */
	public void startGame() {
		this.plugin.game.getScoreManager().initScores();
		this.plugin.game.getGameManager().setIsStart(true);
		this.plugin.game.getScoreboardManager().reloadScoreboard();
		
		
		for (Player player : this.plugin.game.getPlayersManager().getPlayers()) {
			player.setFoodLevel(2000);
			player.setHealth(20.0);
			this.plugin.game.getPlayersManager().initInventory(player);
			player.setGameMode(GameMode.ADVENTURE);
			
			player.setExp(1);
			player.setLevel(1);
			
			for (PotionEffect effect : player.getActivePotionEffects()) {
				player.removePotionEffect(effect.getType());
			}
			
			player.teleport(this.plugin.game.getPlayersManager().getRandomSpawn());

			NmsUtils.sendTitle(player, ChatColor.GOLD + "C'est parti !", "", 5, 20, 5);
		}
		
		
		this.runtaskid = Bukkit.getScheduler().scheduleSyncRepeatingTask(this.plugin, new Runnable() {
			public void run() {
				GameHandler.this.plugin.game.getScoreboardManager().reloadScoreboard();
				String winner = GameHandler.this.plugin.game.getScoreManager().checkIfWinner();
				
				if(winner != null) {
					GameHandler.this.finishGame(winner);
				}
			}
		}, 0L, 1L);
	}
	
	public void finishGame(String winner) {
		Bukkit.getScheduler().cancelTask(this.runtaskid);
		
		for(Player player: this.plugin.game.getPlayersManager().getPlayers()) {
			player.getInventory().clear();
		}
		
		Bukkit.broadcastMessage(ChatColor.GOLD + "Félicitation à " + winner + " qui remporte la partie !");
		
		this.runtaskid = Bukkit.getScheduler().scheduleSyncRepeatingTask(this.plugin, new Runnable() {
			public void run() {
				if (GameHandler.this.finishcount <= -1) {
					GameHandler.this.plugin.getServer().dispatchCommand(GameHandler.this.plugin.getServer().getConsoleSender(), "restart");
				}
				else {
					GameHandler.this.finishcount--;
				}
			}
		}, 0L, 20L);
		
		
	}
}
