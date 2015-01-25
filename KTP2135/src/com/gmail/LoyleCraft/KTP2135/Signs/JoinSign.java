package com.gmail.LoyleCraft.KTP2135.Signs;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import com.gmail.LoyleCraft.KTP2135.KTP2135;
import com.gmail.LoyleCraft.KTP2135.Arena.Arena;

public class JoinSign implements SignType{
	
	private KTP2135 plugin;
	  
	  public JoinSign(KTP2135 plugin)
	  {
	    this.plugin = plugin;
	  }
	  
	  public void handleCreation(SignChangeEvent e)
	  {
	    final Arena arena = this.plugin.amanager.getArenaByName(e.getLine(2));
	    if (arena != null) {
	      e.setLine(0, ChatColor.BLUE + "[KTP2135]");
	      e.getPlayer().sendMessage(ChatColor.GREEN + "Panneau crée avec succès.");
	      this.plugin.signEditor.addSign(e.getBlock(), arena.getArenaName());
	      Bukkit.getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable()
	      {
	        public void run()
	        {
	          JoinSign.this.plugin.signEditor.modifySigns(arena.getArenaName());
	        }
	      });
	    }
	    else
	    {
	      e.getPlayer().sendMessage(ChatColor.RED + "L'arène n'existe pas");
	      e.setCancelled(true);
	      e.getBlock().breakNaturally();
	    }
	  }
	  
	  public void handleClick(PlayerInteractEvent e)
	  {
	    Arena arena = this.plugin.amanager.getArenaByName(((Sign)e.getClickedBlock().getState()).getLine(2));
	    if (arena != null)
	    {
	      boolean canJoin = arena.getPlayerHandler().checkJoin(e.getPlayer());
	      if (canJoin) {
	        arena.getPlayerHandler().spawnPlayer(e.getPlayer());
	      }
	      e.setCancelled(true);
	    }
	    else
	    {
	      e.getPlayer().sendMessage(ChatColor.RED + "L'arène n'existe pas");
	    }
	  }
	  
	  public void handleDestroy(BlockBreakEvent e)
	  {
	    Block b = e.getBlock();
	    this.plugin.signEditor.removeSign(b, ((Sign)b.getState()).getLine(2));
	    e.getPlayer().sendMessage(ChatColor.RED + "Panneau supprimé avec succès.");
	  }
}
