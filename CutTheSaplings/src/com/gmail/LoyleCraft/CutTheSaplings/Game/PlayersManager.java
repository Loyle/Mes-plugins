package com.gmail.LoyleCraft.CutTheSaplings.Game;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.gmail.LoyleCraft.CutTheSaplings.CutTheSaplings;

public class PlayersManager {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private HashMap<String, Player> players = new HashMap();
	private String path = "CutTheSaplings.";

	public CutTheSaplings plugin;

	public PlayersManager(CutTheSaplings pl) {
		this.plugin = pl;
	}

	// Gestionnaire des joueurs
	// Add joueur à la connexion, (on vérifie si on peut démarrer la partie)
	public void addPlayer(Player player) {
		if (this.plugin.getConfig().isSet(path + "Map.Corner1")
				&& this.plugin.getConfig().isSet(path + "Lobby")
				&& this.plugin.getConfig().isSet(path + "Map.Corner2")
				&& this.plugin.getConfig().isSet(path + "Map.SpawnPoint")) {
			this.players.put(player.getName(), player);
			
			
			Double X = this.plugin.getConfig().getDouble(path+"Lobby.X");
			Double Y = this.plugin.getConfig().getDouble(path+"Lobby.Y");
			Double Z = this.plugin.getConfig().getDouble(path+"Lobby.Z");
			Float Yaw = (float) this.plugin.getConfig().getDouble(path+"Lobby.YAW");
			Float Pitch = (float) this.plugin.getConfig().getDouble(path+"Lobby.PITCH");
			World World = Bukkit.getServer().getWorld(this.plugin.getConfig().getString(path+"Lobby.World"));
			
			Location l = new Location(World, X, Y, Z, Yaw, Pitch);
			player.teleport(l);
			
			player.sendMessage(ChatColor.RED +"Bienvenue dans CutTheSaplings !");
			player.sendMessage("");
			player.sendMessage(ChatColor.GOLD +"Le jeu se déroule en deux parties:");
			player.sendMessage(ChatColor.GOLD +"- La récolte, récupérer le plus possible de pousses d'arbres");
			player.sendMessage(ChatColor.GOLD +"- Le pvp, frapper vos adversaires pour leur voler des pousses");
			player.sendMessage("");
			player.sendMessage(ChatColor.GOLD +"Le joueur avec le plus de pousse gagne la partie !");
			player.sendMessage(ChatColor.GOLD +"Bonne chance !");
		} else {
			player.sendMessage(ChatColor.RED
					+ "Bonjour, le plugin CutTheSapling n'est pas encore configuré correctement pour pouvoir être utilisé. Merci de bien vouloir finir la configuration.\nBon jeu !");
		}
		checkStart();
	}

	// Remove joueur (lorsqu'il se déconnecte)
	public void removePlayer(Player player) {
		this.players.remove(player.getName());
		checkStart();
	}

	// Pour récuperer le nombre de joueurs actuellement dans la partie
	public int getNumberPlayers() {
		return this.players.size();
	}

	/*
	 * Fonction qui vérifie si le nombre de joueurs actuellement connecté,
	 * correspond au nombre de joueurs minimal pour pouvoir lancer la partie
	 */

	public void checkStart() {
		if (getNumberPlayers() >= this.plugin.getConfig().getInt(
				path + "MinPlayers")) {
			this.plugin.game.GameHandler.runGameCountdown();
		} else {
			this.plugin.game.GameHandler.stopGameCountdown();
		}
	}

	// Pour get les joueurs actuellement dans la partie
	public Collection<Player> getPlayers() {
		return Collections.unmodifiableCollection(this.players.values());
	}
	
	// On récupère la quantité de saplings contenue dans l'inventaire du joueur
	@SuppressWarnings("deprecation")
	public int getNumberSaplings(Player player) {
		
		PlayerInventory inventory = player.getInventory();
        ItemStack[] items = inventory.getContents();
        int saplings = 0;
        for (ItemStack item : items) {
            if ((item != null) && (item.getTypeId() == 6) && (item.getAmount() > 0))
            {
            	saplings += item.getAmount();
            }
        }
		return saplings;
		
	}
}
