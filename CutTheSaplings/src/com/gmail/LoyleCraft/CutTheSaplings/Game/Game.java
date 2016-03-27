package com.gmail.LoyleCraft.CutTheSaplings.Game;

import com.gmail.LoyleCraft.CutTheSaplings.CutTheSaplings;
import com.gmail.LoyleCraft.CutTheSaplings.Game.PlayersManager;

public class Game {
	
	
	
	// Ce fichier me sert juste à initialiser toutes les class pour le mini-jeu
	public CutTheSaplings plugin;
	public GameManager GameManager;
	public PlayersManager PlayersManager;
	public GameHandler GameHandler;
	
	public Game(CutTheSaplings pl) {
		this.plugin = pl;
		this.GameManager = new GameManager(this.plugin);
		this.PlayersManager = new PlayersManager(this.plugin);
		this.GameHandler = new GameHandler(this.plugin);
	}
}