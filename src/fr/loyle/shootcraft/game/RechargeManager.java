package fr.loyle.shootcraft.game;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import fr.loyle.shootcraft.ShootCraft;

public class RechargeManager {
	private ShootCraft plugin;
	private Player player;
	private double i;
	private int taskid;
	private double exptoadd;
	
	
	public RechargeManager(ShootCraft pl, Player player) {
		this.plugin = pl;
		this.player = player;	
	}
	
	public void recharge(double rechargetime) {
		this.i = 20 * rechargetime;
		this.exptoadd = 1/this.i;
		this.player.setExp(0);
		this.player.setLevel(0);
		
		this.taskid = Bukkit.getScheduler().scheduleSyncRepeatingTask(this.plugin, new Runnable() {
			@Override
			public void run() {
				if (RechargeManager.this.i <= -1) {
					RechargeManager.this.player.setExp(1);
					RechargeManager.this.player.setLevel(1);
					Bukkit.getScheduler().cancelTask(RechargeManager.this.taskid);
				}
				else {
					RechargeManager.this.player.setExp((float) (RechargeManager.this.player.getExp() + RechargeManager.this.exptoadd));
					RechargeManager.this.i--;
				}
			}
		}, 0L, 1L);
	}
	
}
