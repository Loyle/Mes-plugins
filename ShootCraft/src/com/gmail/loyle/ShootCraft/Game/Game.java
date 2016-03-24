package com.gmail.loyle.ShootCraft.Game;

import com.gmail.loyle.ShootCraft.ShootCraft;

public class Game {
	public ShootCraft plugin;
	public GameManager GameManager;
	public PlayersManager PlayersManager;
	
	public Game(ShootCraft pl) {
		this.plugin = pl;
		this.GameManager = new GameManager(this.plugin);
		this.PlayersManager = new PlayersManager(this.plugin);
	}
}
