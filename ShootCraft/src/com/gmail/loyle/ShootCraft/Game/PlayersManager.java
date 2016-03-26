package com.gmail.loyle.ShootCraft.Game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import com.gmail.loyle.ShootCraft.ShootCraft;

public class PlayersManager {
	public ShootCraft plugin;

	private HashMap<UUID, Player> players = new HashMap<>();
	@SuppressWarnings("unused")
	private HashMap<UUID, Player> spectators = new HashMap<>();
	private String path = "ShootCraft.";

	public PlayersManager(ShootCraft pl) {
		this.plugin = pl;
	}

	public void addPlayer(Player player) {
		if (this.plugin.getConfig().isSet(path + "Lobby")) {
			player.teleport(this.getTpLocation("Lobby"));
		}
		else {
			player.sendMessage(ChatColor.RED + "Lobby non définit");
		}

		this.players.put(player.getUniqueId(), player);
		this.plugin.game.GameManager.checkStart();
	}

	public void removePlayer(Player player) {
		this.players.remove(player.getUniqueId());
		this.plugin.game.GameManager.checkStart();
	}

	public int getNumberPlayers() {
		return this.players.size();
	}

	public Collection<Player> getPlayers() {
		return Collections.unmodifiableCollection(this.players.values());
	}

	public boolean checkPlayer(Player player) {
		return this.players.containsKey(player.getUniqueId());
	}

	public Location getTpLocation(String tpPath) {
		if (this.plugin.getConfig().isSet(path + tpPath)) {
			Double x = this.plugin.getConfig().getDouble(path + tpPath + ".X");
			Double y = this.plugin.getConfig().getDouble(path + tpPath + ".Y");
			Double z = this.plugin.getConfig().getDouble(path + tpPath + ".Z");
			Float yaw = (float) this.plugin.getConfig().getDouble(path + tpPath + ".Yaw");
			Float pitch = (float) this.plugin.getConfig().getDouble(path + tpPath + ".Pitch");
			World world = this.plugin.getServer().getWorld(this.plugin.getConfig().getString(path + tpPath + ".World"));

			return new Location(world, x, y, z, yaw, pitch);
		}
		else {
			return new Location(this.plugin.getServer().getWorld("world"), 0, 0, 0, 0, 0);
		}
	}

	public void initInventory(Player player) {
		PlayerInventory inv = player.getInventory();

		inv.clear();
		ItemStack hoe = new ItemStack(Material.WOOD_HOE, 1);

		ItemMeta metaHoe = hoe.getItemMeta();
		
		metaHoe.setDisplayName(ChatColor.GOLD + "Houe en bois");
		
		List<String> lore = new ArrayList<>();
		lore.add(ChatColor.DARK_AQUA + "1.6 seconde(s) pour recharger");
		
		metaHoe.setLore(lore);
		hoe.setItemMeta(metaHoe);
		inv.setItem(0, hoe);
	}
}
