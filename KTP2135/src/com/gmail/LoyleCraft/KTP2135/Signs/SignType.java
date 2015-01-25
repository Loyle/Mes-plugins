package com.gmail.LoyleCraft.KTP2135.Signs;

import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public abstract interface SignType {
	public abstract void handleCreation(SignChangeEvent paramSignChangeEvent);
		  
	public abstract void handleClick(PlayerInteractEvent paramPlayerInteractEvent);
		  
	public abstract void handleDestroy(BlockBreakEvent paramBlockBreakEvent);
}
