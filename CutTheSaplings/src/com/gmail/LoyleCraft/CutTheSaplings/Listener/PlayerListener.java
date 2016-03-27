package com.gmail.LoyleCraft.CutTheSaplings.Listener;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.gmail.LoyleCraft.CutTheSaplings.CutTheSaplings;

public class PlayerListener implements Listener {
	
	public CutTheSaplings plugin;
	
	public PlayerListener(CutTheSaplings pl) {
		this.plugin = pl;
	}
	
	// EVENT connexion: on ajoute le joueur à la liste des participants
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player player = e.getPlayer();
		this.plugin.game.PlayersManager.addPlayer(player);
		e.setJoinMessage(ChatColor.YELLOW + player.getName() + " a rejoint la partie (" + this.plugin.game.PlayersManager.getNumberPlayers() + "/" + this.plugin.game.GameManager.getMaxPlayers() + ")");
		
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title "+player.getName()+" times 20 60 20");
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title "+player.getName()+" subtitle {\"text\":\"Bienvenue sur le mini-jeu CutTheSaplings\",\"color\":\"gold\"}");
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title "+player.getName()+" title {\"text\":\"CutTheSaplings\",\"color\":\"red\"}");
	}
	
	//EVENT déconnexion: On supprime le joueur de la liste des participants
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerDisconnect(PlayerQuitEvent e) {
		Player player = e.getPlayer();
		this.plugin.game.PlayersManager.removePlayer(player);
		e.setQuitMessage(ChatColor.YELLOW + player.getName() + " a quitté la partie (" + this.plugin.game.PlayersManager.getNumberPlayers() + "/" + this.plugin.game.GameManager.getMaxPlayers() + ")");
	}
	
	// EVENT si un block est cassé
	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerBreakBlock(BlockBreakEvent e) {
		// Si la partie est en cours, on entre dans la boucle, sinon on laisse faire
		if(this.plugin.game.GameManager.getIsStart()) {
			// On récupère le joueur qui casse le block
			Player p = e.getPlayer();
			Material sapling = Material.getMaterial(6);
			Material air = Material.getMaterial(0);
			
			// Si le block est une sapling et qu'on se trouve durant la phase de récolte alors on entre dans la condition
			// Sinon on annule l'évenement pour pas que les joueurs casse de blocks
			if(e.getBlock().getType() == sapling && this.plugin.game.GameManager.getPvpStatus() == false) {
				// On annule l'évenement pour casser le block manuellement
				e.setCancelled(true);
				// On récupère les coordonnées du block cassé, on le casse manuellement (pour eviter le loot)
				Location l = e.getBlock().getLocation();
				World world = p.getWorld();
				world.getBlockAt(l).setType(air);
				
				// On ajoute manuellement 1 sapling à l'inventaire du joueur
				PlayerInventory inventory = p.getInventory();
				inventory.addItem(new ItemStack(sapling));
			}
			else {
				e.setCancelled(true);
			}
		}
		else {
			// Pour protéger le lobby (en dehors du moment de jeu, interdit de détruire des blocks)
			e.setCancelled(true);
		}
	}
	
	
	// EVENT pour get une entité qui tape une autre entité
	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerHitPlayer(EntityDamageByEntityEvent e) {
		// On vérifie si les deux entités concerné sont des joueurs
		if(e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
			// Si la partie est en cours on entre dans la condition sinon, on laisse faire
			if(this.plugin.game.GameManager.getIsStart()) {
				// Si on est durant la phase PVP, on entre dans la condition 
				// sinon on cancelled l'event pour bloquer le pvp dans la phase de récolte
				if(this.plugin.game.GameManager.getPvpStatus()) {
					
					// Récupération des joueurs concerné
					Player enemy = (Player) e.getEntity();
					Player damager = (Player) e.getDamager();
					
					PlayerInventory DamagerInv = damager.getInventory();
					PlayerInventory EnemyInv = enemy.getInventory();
					
					Material sapling = Material.getMaterial(6);			
					
					// Petit programme pour enlever 1 sapling à l'énemie touché
					ItemStack[] contents = EnemyInv.getContents();
			        for (int i = 0; i < contents.length; i++) {
			            ItemStack item = contents[i];
			 
			            // Si on trouve des saplings, on en enlève une, puis on sort de la boucle
			            if (item.getType().equals(sapling)) {
			            	int remove = item.getAmount() - 1;
			            	if(item.getAmount() - 1 > 0) {
			            		item.setAmount(remove);
			            		// La personne qui touche, gagne 1 sapling
								DamagerInv.addItem(new ItemStack(sapling));
			            	}
			            	else {
			            		item.setAmount(0);
			            	}
			                break;
			            }
			        }
			        
			        // On set les damages à 0 pour pas prendre de dégàts
			        e.setDamage(0);
				}
				else {
					e.setCancelled(true);
				}
			}
		}
	}
}
