package com.gmail.loyle.shootcraft;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.loyle.shootcraft.commands.MyCommandExecutor;
import com.gmail.loyle.shootcraft.libraries.NmsUtils;
import com.gmail.loyle.shootcraft.game.Game;
import com.gmail.loyle.shootcraft.listener.PlayerListener;

public class ShootCraft extends JavaPlugin {

	@SuppressWarnings("unused")
	private static Plugin plugin;
	public static ShootCraft instance = null;
	public NmsUtils nmsutils;
	public Game game;

	@Override
	public void onEnable() {
		plugin = this;

		// Create/Load config
		this.getConfig().options().copyDefaults(true);
		this.saveDefaultConfig();

		// Load Game system
		this.game = new Game(this);

		// Load librarie NmsUtils
		this.nmsutils = new NmsUtils();

		// Load PlayerListener
		getServer().getPluginManager().registerEvents(new PlayerListener(this), this);

		// Load commands executor
		getCommand("shootcraft").setExecutor(new MyCommandExecutor(this));
	}

	@Override
	public void onDisable() {
		System.out.println("[ShootCraft] Disable (End game ?)");
	}

	public static Boolean hasPermission(Player p, String perm) {
		if (perm.equalsIgnoreCase("")) {
			return Boolean.valueOf(true);
		}
		if (p.isOp()) {
			return Boolean.valueOf(true);
		}
		if (p.hasPermission("ShootCraft.admin")) {
			return Boolean.valueOf(true);
		}
		if (p.hasPermission(perm)) {
			return Boolean.valueOf(true);
		}
		return Boolean.valueOf(false);
	}

	public static ShootCraft getPlugin() {
		return instance;
	}
}
