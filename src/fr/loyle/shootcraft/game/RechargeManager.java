package fr.loyle.shootcraft.game;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import fr.loyle.shootcraft.ShootCraft;

public class RechargeManager {
	private ShootCraft plugin;
	private Player player;
	private double rechargingtime;
	private int taskid;
	
	
	public RechargeManager(ShootCraft pl, Player player) {
		this.plugin = pl;
		this.player = player;	
	}
	
	public void recharge(double rechargetime) {
		this.rechargingtime = 20 * rechargetime;
		
		//final double exptoadd = 7/rechargetime;
		RechargeManager.this.player.setExp(0);
		RechargeManager.this.player.setLevel(0);
		
		this.taskid = Bukkit.getScheduler().scheduleSyncRepeatingTask(this.plugin, new Runnable() {
			@Override
			public void run() {
				if (RechargeManager.this.rechargingtime <= -1) {
					RechargeManager.this.player.setExp(1);
					RechargeManager.this.player.setLevel(1);
					Bukkit.getScheduler().cancelTask(RechargeManager.this.taskid);
				}
				else {
					RechargeManager.this.rechargingtime--;
				}
			}
		}, 0L, 1L);
	}
	
}
