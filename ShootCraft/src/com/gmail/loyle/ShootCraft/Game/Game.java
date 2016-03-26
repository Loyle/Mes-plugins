package com.gmail.loyle.ShootCraft.Game;

import com.gmail.loyle.ShootCraft.ShootCraft;

public class Game {
	public ShootCraft plugin;
	public GameManager GameManager;
	public PlayersManager PlayersManager;
	public GameHandler GameHandler;
	public ScoreboardManager ScoreboardManager;
	
	public Game(ShootCraft pl) {
		this.plugin = pl;
		this.GameManager = new GameManager(this.plugin);
		this.PlayersManager = new PlayersManager(this.plugin);
		this.GameHandler = new GameHandler(this.plugin);
		this.ScoreboardManager = new ScoreboardManager(this.plugin);
	}
}
