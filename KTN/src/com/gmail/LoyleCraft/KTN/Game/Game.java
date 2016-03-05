package com.gmail.LoyleCraft.KTN.Game;

import com.gmail.LoyleCraft.KTN.KTN;
import com.gmail.LoyleCraft.KTN.Game.PlayersManager;

public class Game {
	
	
	
	// Ce fichier me sert juste à initialiser toutes les class pour le jeu
	public KTN plugin;
	public GameManager GameManager;
	public PlayersManager PlayersManager;
	public GameHandler GameHandler;
	public TeamsManager TeamsManager;
	
	public Game(KTN pl) {
		this.plugin = pl;
		this.GameManager = new GameManager(this.plugin);
		this.PlayersManager = new PlayersManager(this.plugin);
		this.GameHandler = new GameHandler(this.plugin);
		this.TeamsManager = new TeamsManager(this.plugin);
	}
}