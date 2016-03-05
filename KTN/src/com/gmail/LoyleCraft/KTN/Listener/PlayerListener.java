package com.gmail.LoyleCraft.KTN.Listener;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.ItemStack;

import com.gmail.LoyleCraft.KTN.KTN;

public class PlayerListener implements Listener {
	
	public KTN plugin;
	private String path = "KTN.";
	private int deathTask;
	private Player playerToKick;
	private int kickCountDown = 0;
	public PlayerListener(KTN pl) {
		this.plugin = pl;
	}
	
	// EVENT connexion: on ajoute le joueur à la liste des participants
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player player = e.getPlayer();
		if(this.plugin.game.GameManager.getIsStart() == false) {		
			this.plugin.game.PlayersManager.addPlayer(player);
			e.setJoinMessage(ChatColor.YELLOW + player.getName() + " a rejoint la partie (" + this.plugin.game.PlayersManager.getNumberPlayers() + "/" + this.plugin.game.GameManager.getMaxPlayers() + ")");
			
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title "+player.getName()+" times 20 60 20");
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title "+player.getName()+" subtitle {\"text\":\"Bienvenue sur KTN\",\"color\":\"gold\"}");
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title "+player.getName()+" title {\"text\":\"KTN\",\"color\":\"red\"}");	
			
			this.plugin.scoreboard.loadSidebar(this.plugin.getConfig().getInt(path+"EpisodeTime"), 0, 1);
		}
		else {
			if(!this.plugin.game.PlayersManager.checkPlayer(player)) {
				player.kickPlayer("Partie déjà en cours");
			}
			else {
				this.plugin.game.PlayersManager.addPlayerInGame(player);
			}
		}
	}
	
	//EVENT déconnexion: On supprime le joueur de la liste des participants
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerDisconnect(PlayerQuitEvent e) {
		if(this.plugin.game.GameManager.getIsStart() == false) {
			Player player = e.getPlayer();
			this.plugin.game.PlayersManager.removePlayer(player);
			e.setQuitMessage(ChatColor.YELLOW + player.getName() + " a quitté la partie (" + this.plugin.game.PlayersManager.getNumberPlayers() + "/" + this.plugin.game.GameManager.getMaxPlayers() + ")");
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onWeahterChange(WeatherChangeEvent e) {
		if(!this.plugin.getConfig().getBoolean(path + "Weather")) {
			if(this.plugin.game.GameManager.getIsStart()) {
				e.setCancelled(true);
			}
			else {
				e.getWorld().setThunderDuration(0);
				e.getWorld().setThundering(false);
			}
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerDeath(PlayerDeathEvent e) {
		Player p = e.getEntity();
		for (Player player : this.plugin.game.PlayersManager.getPlayers()) {
			player.playSound(player.getLocation(), Sound.ENTITY_WITHER_SPAWN, 20, 1);
		}
		this.plugin.game.PlayersManager.removePlayer(p);
		this.plugin.game.PlayersManager.addSpectators(p);
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerRespawn(PlayerRespawnEvent e) {
		Player player = e.getPlayer();
		
		Double X = this.plugin.getConfig().getDouble(path + "Lobby.Spawn.X");
		Double Y = this.plugin.getConfig().getDouble(path + "Lobby.Spawn.Y");
		Double Z = this.plugin.getConfig().getDouble(path + "Lobby.Spawn.Z");
		Float Yaw = (float) this.plugin.getConfig().getDouble(path + "Lobby.Spawn.YAW");
		Float Pitch = (float) this.plugin.getConfig().getDouble(path + "Lobby.Spawn.PITCH");
		World World = Bukkit.getServer().getWorld(this.plugin.getConfig().getString(path + "Lobby.WorldName"));

		Location l = new Location(World, X, Y, Z, Yaw, Pitch);
		player.setBedSpawnLocation(l);
		player.teleport(l);
		player.setGameMode(GameMode.SPECTATOR);
		
		this.playerToKick = player;
		this.kickCountDown = this.plugin.getConfig().getInt(path + "KickOnDeath.After");
		
		if(this.plugin.getConfig().getBoolean(path + "KickOnDeath.Kick")) {
			this.deathTask = Bukkit.getScheduler().scheduleSyncRepeatingTask(this.plugin, new Runnable() {
		    	public void run() {
		    		if(PlayerListener.this.kickCountDown <= -1) {
		    			PlayerListener.this.playerToKick.kickPlayer("JayJay");
		    			Bukkit.getScheduler().cancelTask(PlayerListener.this.deathTask);
		    		}
		    		else {
		    			PlayerListener.this.kickCountDown--;
		    		}
		    	}
		    },0L,20L);
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerDamage(EntityDamageEvent e) {
		if(e.getEntity() instanceof Player) {
		    if(!this.plugin.game.GameManager.getDamageStatus()) {
		    	e.setCancelled(true);
		    }
		    else if(this.plugin.game.GameManager.getIsStart() == false) {
		    	e.setCancelled(true);
		    }
		    return;
		}
		return;
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.HIGHEST)
    public void craftItem(PrepareItemCraftEvent e) {
        Material itemType = e.getRecipe().getResult().getType();
        Byte itemData = e.getRecipe().getResult().getData().getData();
        if((itemType==Material.GOLDEN_APPLE&&itemData==1) || (itemType==Material.POTION && itemData==8193 || itemData == 16385 || itemData == 16417 || itemData == 8225)) {
            e.getInventory().setResult(new ItemStack(Material.AIR));
        }
        if(this.plugin.getConfig().getBoolean(path+"PotionLvlDeux")) {
        	if(((itemType==Material.POTION) 
        			// POTION II
        			&& itemData == 8226 || itemData == 8226 || itemData == 8228 || itemData == 8229 || itemData == 8233 || itemData == 8236 
        			// POTION DIVERS
        			|| itemData == 8257 || itemData == 8289 || itemData == 8290 || itemData == 8292 || itemData == 8297 
        			// POTION SPASH 2
        			|| itemData == 16418 || itemData == 16417 || itemData == 16420 || itemData == 16421 || itemData == 16425 || itemData == 16428)) {
                e.getInventory().setResult(new ItemStack(Material.AIR));
            }
        }
    }
	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerInteractEvent(PlayerInteractEvent e) {
		if(this.plugin.game.GameManager.getIsStart() == false) {
			Player player = e.getPlayer();
		    
			Action action = e.getAction();
		    
			ItemStack hand = player.getItemInHand();
			if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
				for(int i=1; i<= this.plugin.getConfig().getInt(path + "Teams.Numbers"); i++) {
					if(hand.getType().equals(Material.BANNER) && hand.getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Rejoint la team "+this.plugin.game.TeamsManager.getName(i))) {
						this.plugin.game.TeamsManager.joinTeam(i, player);
						e.setCancelled(true);
						return;
					}
				}
			}
		}
	}
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerMoveItem(InventoryClickEvent e) {
		if(this.plugin.game.GameManager.getIsStart() == false) {
			ItemStack clicked = e.getCurrentItem();
			Player player = (Player) e.getWhoClicked();
			for(int i=1; i<= this.plugin.getConfig().getInt(path + "Teams.Numbers"); i++) {
				if(clicked.getType().equals(Material.BANNER) && clicked.getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Rejoint la team "+this.plugin.game.TeamsManager.getName(i))) {
					this.plugin.game.TeamsManager.joinTeam(i, player);
					e.setCancelled(true);
					return;
				}
			}
		}
	}
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerDropItem(PlayerDropItemEvent e) {
		if(this.plugin.game.GameManager.getIsStart() == false) {
			ItemStack drop = e.getItemDrop().getItemStack();
			for(int i=1; i<= this.plugin.getConfig().getInt(path + "Teams.Numbers"); i++) {
				if(drop.getType().equals(Material.BANNER) && drop.getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Rejoint la team "+this.plugin.game.TeamsManager.getName(i))) {
					e.setCancelled(true);
					return;
				}
			}
		}
	}
}
