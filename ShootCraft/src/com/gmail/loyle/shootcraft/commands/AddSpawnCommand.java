package com.gmail.loyle.shootcraft.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.gmail.loyle.shootcraft.ShootCraft;

public class AddSpawnCommand implements BasicCommand{
	private ShootCraft plugin;

	public AddSpawnCommand(ShootCraft pl) {
		this.plugin = pl;
	}

	@Override
	public boolean onCommand(Player player, String[] args) {
		if (ShootCraft.hasPermission(player, getPermission()).booleanValue()) {
			this.plugin.game.getGameManager().addSpawn(player.getLocation());
			player.sendMessage(ChatColor.GREEN + "Spawn ajouté");
			return true;
		}
		return true;
	}

	@Override
	public String help(Player p) {
		if (ShootCraft.hasPermission(p, getPermission()).booleanValue()) {
			return "/shootcraft addspawn - Ajouter un spawn sur la map.";
		}
		return "";
	}

	@Override
	public String getPermission() {
		return "ShootCraft.Admin";
	}
}
