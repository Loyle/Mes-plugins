package com.gmail.LoyleCraft.KTN.Game;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Difficulty;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;

import com.gmail.LoyleCraft.KTN.KTN;

public class GameManager {
	
	private String path = "KTN.";
	public KTN plugin;
	private boolean isStarting = false;
	private boolean isStart = false;
	private boolean PvpStatus = false;
	private boolean damageStatus = true;
	
	public GameManager(KTN pl) {
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
	// Fonction exécuté par la commande setlobbyspawn (qui set le lobby)
	public void setLobbySpawnLocation(Location l) {
		this.plugin.getConfig().set(path+"Lobby.Spawn.X", l.getX());
		this.plugin.getConfig().set(path+"Lobby.Spawn.Y", l.getY());
		this.plugin.getConfig().set(path+"Lobby.Spawn.Z", l.getZ());
		this.plugin.getConfig().set(path+"Lobby.Spawn.YAW", l.getYaw());
		this.plugin.getConfig().set(path+"Lobby.Spawn.PITCH", l.getPitch());
		this.plugin.getConfig().set(path+"Lobby.WorldName", l.getWorld().getName());
		this.plugin.saveConfig();
	}
	
	public void checkStart(Player player) {
		if(this.getIsStart() == false && this.getIsStarting() == false) {
			player.sendMessage(ChatColor.GOLD + "Demarrage de la partie");
			this.plugin.game.GameHandler.runGameCountdown();
		}
		else {
			player.sendMessage(ChatColor.RED + "La partie est déjà en cours !");
		}
	}

	public boolean getDamageStatus() {
		return this.damageStatus;
	}
	public void setDamageStatus(boolean status) {
		this.damageStatus = status;
	}
	
	// Fonction pour générer une map avec une génération de saplings random

	public void initialiseWorld() {
		World lobby = this.plugin.getServer().getWorld(this.plugin.getConfig().getString(path+"Lobby.WorldName"));
		World world = this.plugin.getServer().getWorld(this.plugin.getConfig().getString(path+"Map.WorldName"));
		World world_nether = this.plugin.getServer().getWorld(this.plugin.getConfig().getString(path+"Map.WorldName") + "_nether");
		
		if(lobby == null) {
			System.out.println("[KTN] Generation du lobby ...");
			this.plugin.getServer().createWorld(new WorldCreator(this.plugin.getConfig().getString(path+"Lobby.WorldName")));
			System.out.println("[KTN] Fin de la generation du lobby ...");
		}
		if(world == null) {
			System.out.println("[KTN] Generation de la map...");
			this.plugin.getServer().createWorld(new WorldCreator(this.plugin.getConfig().getString(path+"Map.WorldName")));
			System.out.println("[KTN] Fin de la generation de la map");
		}
		else {
			System.out.println("[KTN] Tout les mondes deja generer");
		}
		
		
		world.setGameRuleValue("doDaylightCycle", this.plugin.getConfig().getString(path+"DoLightCycle.Do"));
		world.setGameRuleValue("naturalRegeneration", this.plugin.getConfig().getString(path+"NaturalRegeneration"));
		world.setGameRuleValue("randomTickSpeed", "3");
		world.setGameRuleValue("commandBlockOutput", "false");
		world.setGameRuleValue("logAdminCommands", "false");
		world.setTime(this.plugin.getConfig().getLong(path+"DoLightCycle.Time"));
		world.setThundering(false);
		world.setStorm(false);
		world.setDifficulty(Difficulty.HARD);
		
		
		world_nether.setGameRuleValue("naturalRegeneration", this.plugin.getConfig().getString(path+"NaturalRegeneration"));
		world_nether.setGameRuleValue("randomTickSpeed", "3");
		world_nether.setGameRuleValue("commandBlockOutput", "false");
		world_nether.setGameRuleValue("logAdminCommands", "false");
		world_nether.setTime(this.plugin.getConfig().getLong(path+"DoLightCycle.Time"));
		world_nether.setDifficulty(Difficulty.HARD);
		
		lobby.setTime(6000);
		lobby.setGameRuleValue("doDaylightCycle", "false");
		lobby.setGameRuleValue("randomTickSpeed", "3");
		lobby.setGameRuleValue("commandBlockOutput", "false");
		lobby.setGameRuleValue("logAdminCommands", "false");
		lobby.setThundering(false);
		lobby.setStorm(false);	
		
		
		// Problème d'incompatibilité en 1.9
		/*WorldBorder border = world.getWorldBorder();
		System.out.println(border);
		border.setSize(this.plugin.getConfig().getDouble(path+"Map.Size"));
		border.setCenter(0,0);
		border.setDamageAmount(1);
		border.setWarningDistance(50);*/
	}
}
