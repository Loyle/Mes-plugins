package fr.loyle.shootcraft.game;


import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;

import fr.loyle.shootcraft.ShootCraft;

public class ScoreManager {
	
	private ShootCraft plugin;
	private String path = "ShootCraft.";
	private HashMap<UUID, Integer> scores = new HashMap<>();
		
	public ScoreManager(ShootCraft pl) {
		this.plugin = pl;
	}
	
	public int getScore(Player player) {
		return this.scores.get(player.getUniqueId());
	}
	
	public void addScore(Player player, int valuetoadd) {
		this.scores.put(player.getUniqueId(), this.getScore(player) + valuetoadd);
	}
	
	public void initScores() {
		this.scores.clear();
		
		for (Player player : this.plugin.game.getPlayersManager().getPlayers()) {
			this.scores.put(player.getUniqueId(), 0);
		}
	}
	
	public String checkIfWinner() {
		for (Player player : this.plugin.game.getPlayersManager().getPlayers()) {
			if(this.scores.get(player.getUniqueId()) >= this.plugin.getConfig().getInt(path + "MaxKills")) {
				return player.getName();
			}
		}
		return null;
	}
}
