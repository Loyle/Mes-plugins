package fr.loyle.shootcraft;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import fr.loyle.shootcraft.commands.MyCommandExecutor;
import fr.loyle.shootcraft.game.Game;
import fr.loyle.shootcraft.libraries.NmsUtils;
import fr.loyle.shootcraft.listener.PlayerListener;

public class ShootCraft extends JavaPlugin {

	public NmsUtils nmsutils;
	public Game game;

	@Override
	public void onEnable() {

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

	public Boolean hasPermission(Player player, String perm) {
		if (perm.equalsIgnoreCase("")) {
			return true;
		}
		if (player.isOp()) {
			return true;
		}
		if (player.hasPermission("ShootCraft.admin")) {
			return true;
		}
		if (player.hasPermission(perm)) {
			return true;
		}
		return false;
	}
}
