package fr.loyle.shootcraft.game;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import net.minecraft.server.v1_9_R1.EnumParticle;
import net.minecraft.server.v1_9_R1.PacketPlayOutWorldParticles;

import org.bukkit.ChatColor;
import org.bukkit.EntityEffect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.util.Vector;

import fr.loyle.shootcraft.ShootCraft;
import fr.loyle.shootcraft.libraries.NmsUtils;

public class PlayersManager {
	private ShootCraft plugin;

	private HashMap<UUID, Player> players = new HashMap<>();
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
		
		player.setExp(0);
		player.getInventory().clear();
		player.setGameMode(GameMode.ADVENTURE);
		player.setFoodLevel(2000);
		player.setHealth(20.0);
		
		for (PotionEffect effect : player.getActivePotionEffects()) {
			player.removePotionEffect(effect.getType());
		}
		
		NmsUtils.sendTitle(player, ChatColor.GOLD + "ShootCraft", ChatColor.RED + "Bienvenue dans le mini-jeu ShootCraft", 0, 80, 10);
		
		this.plugin.game.getScoreboardManager().joinTeam(player);
		
		this.players.put(player.getUniqueId(), player);
		this.plugin.game.getGameManager().checkStart();
	}

	public void removePlayer(Player player) {
		this.players.remove(player.getUniqueId());
		this.plugin.game.getScoreboardManager().leaveTeam(player);
		this.plugin.game.getGameManager().checkStart();
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

	/*
	 * 
	 * Spectators managers !
	 */
	public void addSpectator(Player player) {
		player.setGameMode(GameMode.SPECTATOR);
		player.teleport(this.getRandomSpawn());
		player.sendMessage(ChatColor.RED + "La partie est en cours...");
		this.spectators.put(player.getUniqueId(), player);
	}

	public void removeSpectator(Player player) {
		this.spectators.remove(player.getUniqueId());
	}

	/*
	 * 
	 * Functions for players
	 */
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

	public Location getRandomSpawn() {

		File spawnfile = new File("plugins/ShootCraft/Spawns.yml");
		FileConfiguration spawns = YamlConfiguration.loadConfiguration(spawnfile);

		int i = 1;
		while (spawns.isSet("" + i)) {
			i++;
		}

		i--;

		int random = (int) (Math.random() * (i + 1 - 1)) + 1;

		Double x = spawns.getDouble(random + ".X");
		Double y = spawns.getDouble(random + ".Y");
		Double z = spawns.getDouble(random + ".Z");
		Float yaw = (float) spawns.getDouble(random + ".Yaw");
		Float pitch = (float) spawns.getDouble(random + ".Pitch");
		World world = this.plugin.getServer().getWorld(spawns.getString(random + ".World"));

		return new Location(world, x, y, z, yaw, pitch);
	}

	public void initInventory(Player player) {
		PlayerInventory inv = player.getInventory();

		inv.clear();
		ItemStack hoe = new ItemStack(Material.WOOD_HOE, 1);

		ItemMeta metaHoe = hoe.getItemMeta();

		metaHoe.setDisplayName(ChatColor.GOLD + "Houe en bois");

		List<String> lore = new ArrayList<>();
		lore.add(ChatColor.DARK_AQUA + "1,6 seconde(s) pour recharger");

		metaHoe.setLore(lore);
		hoe.setItemMeta(metaHoe);
		inv.setItem(0, hoe);
	}

	/*
	 * 
	 * Get targeting player !
	 */

	// Find Entity object a Player is looking at (or null)
	public Player shootPlayer(Player p) {
		Location start = p.getEyeLocation();
		Vector increase = start.getDirection();
		for (int i = 0; i < 50; i++) { // ici distance == 50
			Location point = start.add(increase);
			PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(EnumParticle.FIREWORKS_SPARK, true, (float) point.getX(), (float) point.getY(), (float) point.getZ(), 0, 0, 0, 0, 0, null);
			((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
			if (point.getBlock().getType().isSolid()) {
				// ici on rencontre un bloc, on annule histoire d'évité de tirer
				// à travers les murs :)
				return null;
			}
			else {
				for (Entity en : point.getChunk().getEntities()) {
					if (en != p && en.getType().equals(EntityType.PLAYER)) {
						Player p2 = (Player) en;
						if (p2.getLocation().distance(point) < 1.0 || p2.getEyeLocation().distance(point) < 1.0) {
							if (p2.getGameMode() == GameMode.ADVENTURE || p2.getGameMode() == GameMode.SURVIVAL) {
								// ici p2 est le joueur mort
								// p est le joueur ayant tiré
								p2.playEffect(EntityEffect.HURT);

								return p2;
							}
						}
					}
				}
			}
		}
		return null;
	}
}
