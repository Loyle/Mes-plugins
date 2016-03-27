package com.gmail.LoyleCraft.CutTheSaplings.Game;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

import com.gmail.LoyleCraft.CutTheSaplings.CutTheSaplings;

public class GameManager {
	
	private String path = "CutTheSaplings.";
	public CutTheSaplings plugin;
	private boolean isStarting = false;
	private boolean isStart = false;
	private boolean PvpStatus = false;
	
	public GameManager(CutTheSaplings pl) {
		this.plugin = pl;
	}
	
	// ICI gestionnaire du jeu et de la map
	
	public int getMaxPlayers() {
		return  this.plugin.getConfig().getInt(path + "MaxPlayers");
	}
	public int getMinPlayers() {
		return  this.plugin.getConfig().getInt(path + "MinPlayers");
	}
	
	// Getters/Setters pour gérer les phases du jeu
	public boolean getIsStarting() {
		return isStarting;
	}
	public void setIsStarting(boolean status) {
		this.isStarting = status;
	}
	
	public boolean getIsStart() {
		return isStart;
	}
	public void setIsStart(boolean status) {
		this.isStart = status;
	}
	
	public boolean getPvpStatus() {
		return PvpStatus;
	}
	public void setPvpStatus(boolean status) {
		this.PvpStatus = status;
	}
	
	// Fonction exécuté par la commande setcorner (qui set les angles de la map)
	public void setCorner(String corner, Location l) {
		this.plugin.getConfig().set(path+"Map.Corner"+corner+".X", (int)l.getX());
		this.plugin.getConfig().set(path+"Map.Corner"+corner+".Y", (int)l.getY());
		this.plugin.getConfig().set(path+"Map.Corner"+corner+".Z", (int)l.getZ());
		this.plugin.getConfig().set(path+"Map.Corner"+corner+".World", l.getWorld().getName());
		this.plugin.saveConfig();
	}
	// Fonction exécuté par la commande setspawn (qui set le spawn de la map)
	public void setSpawnLocation(Location l) {
		this.plugin.getConfig().set(path+"Map.SpawnPoint.X", l.getX());
		this.plugin.getConfig().set(path+"Map.SpawnPoint.Y", l.getY());
		this.plugin.getConfig().set(path+"Map.SpawnPoint.Z", l.getZ());
		this.plugin.getConfig().set(path+"Map.SpawnPoint.YAW", l.getYaw());
		this.plugin.getConfig().set(path+"Map.SpawnPoint.PITCH", l.getPitch());
		this.plugin.getConfig().set(path+"Map.SpawnPoint.World", l.getWorld().getName());
		this.plugin.saveConfig();
	}
	// Fonction exécuté par la commande setlobbyspawn (qui set le lobby)
	public void setLobbySpawnLocation(Location l) {
		this.plugin.getConfig().set(path+"Lobby.X", l.getX());
		this.plugin.getConfig().set(path+"Lobby.Y", l.getY());
		this.plugin.getConfig().set(path+"Lobby.Z", l.getZ());
		this.plugin.getConfig().set(path+"Lobby.YAW", l.getYaw());
		this.plugin.getConfig().set(path+"Lobby.PITCH", l.getPitch());
		this.plugin.getConfig().set(path+"Lobby.World", l.getWorld().getName());
		this.plugin.saveConfig();
	}
	
	// Fonction pour générer une map avec une génération de saplings random
	
	/*
	 * 
	 * 
	 * ATTENTION
	 * La fonction ci-dessous est juste affreuse niveau fonctionnement ne chercher pas à comprendre, c'est bizarre xD 
	 * Et je suis pas sur que ca fonctionne dans tous les cas possibles
	 * J'ai préférer ne pas l'argumenter en profondeur
	 * 
	 * 
	 */
	
	@SuppressWarnings("deprecation")
	public void iniMap() {
		World world = this.plugin.getServer().getWorld(this.plugin.getConfig().getString(path+"Map.Corner1.World"));
		
		world.setTime(6000);
		
		
		world.setGameRuleValue("doDaylightCycle", "false");
		world.setGameRuleValue("randomTickSpeed", "0");
		world.setGameRuleValue("commandBlockOutput", "false");
		world.setGameRuleValue("sendCommandFeedback", "false");
		world.setGameRuleValue("logAdminCommands", "false");
		
		Material sapling = Material.getMaterial(6);
		Material air = Material.getMaterial(0);
		Material grass = Material.getMaterial(2);
		Material dirt = Material.getMaterial(3);
		
		int Y = this.plugin.getConfig().getInt(path+"Map.Corner1.Y");
		
		int C1X = this.plugin.getConfig().getInt(path+"Map.Corner1.X");
		int C1Z = this.plugin.getConfig().getInt(path+"Map.Corner1.Z");
		
		int C2X = this.plugin.getConfig().getInt(path+"Map.Corner2.X");
		int C2Z = this.plugin.getConfig().getInt(path+"Map.Corner2.Z");
		
		int defaultC1X = C1X;
		
		while(C1X != C2X || C1Z != C2Z) {
			//Génération d'un aléatoire
			int random = (int) (Math.random() * 6 );
			// Si plus grand ou égale à 4 alors on place de l'air sinon on place une sapling
			if(random >= 4) {
				// Si le block en dessous est de la terre/herbe, on place de l'air
				if(world.getBlockAt(C1X, Y-1, C1Z).getType() == grass || world.getBlockAt(C1X, Y-1, C1Z).getType() == dirt) {
					world.getBlockAt(C1X, Y, C1Z).setType(air);
				}
			}
			else {
				// Si le block en dessous est de la terre/herbe, on place une sapling
				if(world.getBlockAt(C1X, Y-1, C1Z).getType() == grass || world.getBlockAt(C1X, Y-1, C1Z).getType() == dirt) {
					world.getBlockAt(C1X, Y, C1Z).setType(sapling);
				}
			}
			
			/*
			 * 
			 * C1X= Corner 1 X
			 * C1Z= Corner 1 Z
			 * 
			 * C2X= Corner 2 X
			 * C2Z= Corner 2 Z
			 * 
			 * DefaultC1X = la valeur du début de C1X
			 * 
			 */
			
			// Décallage d'un block en X, si on est arrivé au X du corner 2 alors on décalle la pose du block en Z etc.....
			// J'ai pas mal de temps à le faire :/
			if(C1X == C2X) {
				if(C1Z < C2Z) {
					C1Z++;
				}
				else {
					C1Z--;
				}
				C1X = defaultC1X;
			}
			else {
				if(C1X < C2X) {
					C1X++;
				}
				else {
					C1X--;
				}
			}
		}
		
		//On finit par poser une sapling au dernier block
		if(world.getBlockAt(C1X, Y-1, C1Z).getType() == grass || world.getBlockAt(C1X, Y-1, C1Z).getType() == dirt) {
			world.getBlockAt(C1X, Y, C1Z).setType(sapling);
		}
	}
}
