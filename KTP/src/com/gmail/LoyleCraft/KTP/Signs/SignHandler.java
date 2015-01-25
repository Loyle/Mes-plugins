package com.gmail.LoyleCraft.KTP.Signs;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import com.gmail.LoyleCraft.KTP.KTP;

public class SignHandler implements Listener {
	  @SuppressWarnings({ "unchecked", "rawtypes" })
	private HashMap<String, SignType> signs = new HashMap();
	  
	  public SignHandler(KTP plugin)
	  {
	    this.signs.put("[join]", new JoinSign(plugin));
	  }
	  
	  @EventHandler(priority=EventPriority.NORMAL, ignoreCancelled=true)
	  public void onTNTRunSignCreate(SignChangeEvent e)
	  {
	    Player player = e.getPlayer();
	    if ((e.getLine(0).equalsIgnoreCase("[KTP]")) || (e.getLine(0).equalsIgnoreCase(ChatColor.BLUE + "[ktp]")))
	    {
	      if (!player.hasPermission("ktp.edit"))
	      {
	        player.sendMessage(ChatColor.RED + "Vous n'avez pas la permission.");
	        e.setCancelled(true);
	        e.getBlock().breakNaturally();
	        return;
	      }
	      String line = e.getLine(1).toLowerCase();
	      if (this.signs.containsKey(line)) {
	        ((SignType)this.signs.get(line)).handleCreation(e);
	      }
	    }
	  }
	  
	  @EventHandler(priority=EventPriority.NORMAL)
	  public void onSignClick(PlayerInteractEvent e)
	  {
	    if (e.getAction() != Action.RIGHT_CLICK_BLOCK) {
	      return;
	    }
	    if (!(e.getClickedBlock().getState() instanceof Sign)) {
	      return;
	    }
	    Sign sign = (Sign)e.getClickedBlock().getState();
	    if (sign.getLine(0).equalsIgnoreCase(ChatColor.BLUE + "[KTP]"))
	    {
	      String line = sign.getLine(1).toLowerCase();
	      if (this.signs.containsKey(line)) {
	        ((SignType)this.signs.get(line)).handleClick(e);
	      }
	    }
	  }
	  
	  @EventHandler(priority=EventPriority.NORMAL, ignoreCancelled=true)
	  public void onSignDestroy(BlockBreakEvent e)
	  {
	    if (!(e.getBlock().getState() instanceof Sign)) {
	      return;
	    }
	    Player player = e.getPlayer();
	    Sign sign = (Sign)e.getBlock().getState();
	    if (sign.getLine(0).equalsIgnoreCase(ChatColor.BLUE + "[KTP]"))
	    {
	      if (!player.hasPermission("ktp.edit"))
	      {
	        player.sendMessage(ChatColor.RED + "Vous n'avez pas la permission.");
	        e.setCancelled(true);
	        return;
	      }
	      String line = sign.getLine(1).toLowerCase();
	      if (this.signs.containsKey(line)) {
	        ((SignType)this.signs.get(line)).handleDestroy(e);
	      }
	    }
	  }
}
