package fr.loyle.shootcraft.game;

import fr.loyle.shootcraft.ShootCraft;

public class Game {
	private ShootCraft plugin;
	private GameManager gamemanager;
	private PlayersManager playersmanager;
	private GameHandler gamehandler;
	private ScoreboardManager scoreboardmanager;
	private ScoreManager scoremanager;
	
	
	public Game(ShootCraft pl) {
		this.plugin = pl;
		this.gamemanager = new GameManager(this.plugin);
		this.playersmanager = new PlayersManager(this.plugin);
		this.gamehandler = new GameHandler(this.plugin);
		this.scoreboardmanager = new ScoreboardManager(this.plugin);
		this.scoremanager = new ScoreManager(this.plugin);
	}
	
	public ScoreboardManager getScoreboardManager() {
		return this.scoreboardmanager;
	}
	
	public GameHandler getGameHandler() {
		return this.gamehandler;
	}
	
	public PlayersManager getPlayersManager() {
		return this.playersmanager;
	}
	
	public GameManager getGameManager() {
		return this.gamemanager;
	}
	
	public ScoreManager getScoreManager() {
		return this.scoremanager;
	}
}
