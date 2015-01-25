package com.gmail.LoyleCraft.KTP.Arena;

import java.util.Collection;
import java.util.HashMap;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class PlayerDataStore {	
	private HashMap<String, ItemStack[]> plinv = new HashMap();
	private HashMap<String, ItemStack[]> plarmor = new HashMap();
	private HashMap<String, Collection<PotionEffect>> pleffects = new HashMap();
	private HashMap<String, Location> plloc = new HashMap();
	private HashMap<String, Integer> plhunger = new HashMap();
	private HashMap<String, GameMode> plgamemode = new HashMap();
	
	public void storePlayerInventory(Player player)
	{
		PlayerInventory pinv = player.getInventory();
	    this.plinv.put(player.getName(), pinv.getContents());
	    pinv.clear();
	 }
	
	public void storePlayerArmor(Player player)
	{
	    PlayerInventory pinv = player.getInventory();
	    this.plarmor.put(player.getName(), pinv.getArmorContents());
	    pinv.setArmorContents(null);
	}
	
	public void storePlayerPotionEffects(Player player)
	{
	    Collection<PotionEffect> peff = player.getActivePotionEffects();
	    this.pleffects.put(player.getName(), peff);
	    for (PotionEffect peffect : peff) {
	      player.removePotionEffect(peffect.getType());
	    }
	}
	  
	public void storePlayerLocation(Player player)
	{
	    this.plloc.put(player.getName(), player.getLocation());
	}
	
	public void storePlayerHunger(Player player)
	{
	    this.plhunger.put(player.getName(), Integer.valueOf(player.getFoodLevel()));
	    player.setFoodLevel(20);
	}
	
	public void storePlayerGameMode(Player player)
	{
	    this.plgamemode.put(player.getName(), player.getGameMode());
	    player.setGameMode(GameMode.SURVIVAL);
	}
	
	public void restorePlayerInventory(Player player)
	{
	    player.getInventory().setContents((ItemStack[])this.plinv.get(player.getName()));
	    this.plinv.remove(player.getName());
	}
	
	public void restorePlayerArmor(Player player)
	{
	    player.getInventory().setArmorContents((ItemStack[])this.plarmor.get(player.getName()));
	    this.plarmor.remove(player.getName());
	}
	  
	public void restorePlayerPotionEffects(Player player)
	{
	    player.addPotionEffects((Collection)this.pleffects.get(player.getName()));
	    this.pleffects.remove(player.getName());
	}
	
	public void restorePlayerLocation(Player player)
	{
	    player.teleport((Location)this.plloc.get(player.getName()));
	    this.plloc.remove(player.getName());
	}
	
	public void clearPlayerLocation(Player player)
	{
	    this.plloc.remove(player.getName());
	}
	
	public void restorePlayerHunger(Player player)
	{
	    player.setFoodLevel(((Integer)this.plhunger.get(player.getName())).intValue());
	    this.plhunger.remove(player.getName());
	}
	  
	public void restorePlayerGameMode(Player player)
	{
	    player.setGameMode((GameMode)this.plgamemode.get(player.getName()));
	    this.plgamemode.remove(player.getName());
	}

}
