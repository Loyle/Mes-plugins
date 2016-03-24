package com.gmail.loyle.ShootCraft.Listener;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import com.gmail.loyle.ShootCraft.ShootCraft;
import com.gmail.loyle.ShootCraft.Libraries.NmsUtils;

public class PlayerListener implements Listener {
	
	public ShootCraft plugin;
	public PlayerListener(ShootCraft pl) {
		this.plugin = pl;
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player player = e.getPlayer();
		NmsUtils.sendTitle(player, ChatColor.GOLD + "ShootCraft", ChatColor.RED + "Bienvenue dans le mini-jeu ShootCraft", 1, 5, 1);
	}

}
