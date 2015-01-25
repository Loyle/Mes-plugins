package com.gmail.LoyleCraft.KTP2135.Signs;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.gmail.LoyleCraft.KTP2135.KTP2135;
import com.gmail.LoyleCraft.KTP2135.Arena.Arena;

public class SignEditor {
		  private KTP2135 plugin;
		  @SuppressWarnings({ "unchecked", "rawtypes" })
		private HashMap<String, HashSet<SignInfo>> signs = new HashMap();
		  private File configfile;
		  
		  public SignEditor(KTP2135 plugin)
		  {
		    this.plugin = plugin;
		    this.configfile = new File(plugin.getDataFolder().getAbsolutePath() + File.separator + "signs.yml");
		  }
		  
		  @SuppressWarnings({ "unchecked", "rawtypes" })
		public void addArena(String arena)
		  {
		    if (!this.signs.containsKey(arena)) {
		      this.signs.put(arena, new HashSet());
		    }
		  }
		  
		  public void removeArena(String arena)
		  {
		    for (Block block : getSignsBlocks(arena)) {
		      removeSign(block, arena);
		    }
		    this.signs.remove(arena);
		  }
		  
		  public void addSign(Block block, String arena)
		  {
		    SignInfo signinfo = new SignInfo(block);
		    addArena(arena);
		    getSigns(arena).add(signinfo);
		  }
		  
		  public void removeSign(Block block, String arena)
		  {
		    if ((block.getState() instanceof Sign))
		    {
		      Sign sign = (Sign)block.getState();
		      sign.setLine(0, "");
		      sign.setLine(1, "");
		      sign.setLine(2, "");
		      sign.setLine(3, "");
		      sign.update();
		    }
		    addArena(arena);
		    getSigns(arena).remove(getSignInfo(block, arena));
		  }
		  
		  public HashSet<Block> getSignsBlocks(String arena)
		  {
		    @SuppressWarnings({ "unchecked", "rawtypes" })
			HashSet<Block> signs = new HashSet();
		    for (SignInfo signinfo : getSigns(arena))
		    {
		      Block block = signinfo.getBlock();
		      if (block != null) {
		        signs.add(block);
		      }
		    }
		    return signs;
		  }
		  
		  private SignInfo getSignInfo(Block block, String arena)
		  {
		    for (SignInfo si : getSigns(arena)) {
		      if (si.getBlock().equals(block)) {
		        return si;
		      }
		    }
		    return new SignInfo(block);
		  }
		  
		  private void addSignInfo(SignInfo si, String arena)
		  {
		    addArena(arena);
		    getSigns(arena).add(si);
		  }
		  
		  @SuppressWarnings({ "unchecked", "rawtypes" })
		private HashSet<SignInfo> getSigns(String arena)
		  {
		    addArena(arena);
		    return (HashSet)this.signs.get(arena);
		  }
		  
		  @SuppressWarnings("unused")
		public void modifySigns(String arenaname)
		  {
		    try
		    {
		      Arena arena = this.plugin.amanager.getArenaByName(arenaname);
		      if (arena == null) {
		        return;
		      }
		      String text = null;
		      int players = arena.getPlayersManager().getCount();
		      int maxPlayers = arena.getStructureManager().getMaxPlayers();
		      if (!arena.getStatusManager().isArenaEnabled()) {
		        text = ChatColor.RED.toString() + ChatColor.BOLD.toString() + "Disabled";
		      } else if (arena.getStatusManager().isArenaRunning()) {
		        text = ChatColor.RED.toString() + ChatColor.BOLD.toString() + "En cours...";
		      } else if (players == maxPlayers) {
		        text = ChatColor.RED.toString() + ChatColor.BOLD.toString() + Integer.toString(players) + "/" + Integer.toString(maxPlayers);
		      } else if (arena.getStatusManager().isArenaStarting()) {
			        text = ChatColor.RED.toString() + ChatColor.BOLD.toString() + Integer.toString(players) + "/" + Integer.toString(maxPlayers);
			  } else {
		        text = ChatColor.GREEN.toString() + ChatColor.BOLD.toString() + Integer.toString(players) + "/" + Integer.toString(maxPlayers);
		      }
		      for (Block block : getSignsBlocks(arenaname)) {
		        if ((block.getState() instanceof Sign))
		        {
		          Sign sign = (Sign)block.getState();
		          sign.setLine(3, text);
		          sign.update();
		        }
		        else
		        {
		          removeSign(block, arenaname);
		        }
		      }
		    }
		    catch (Exception e)
		    {
		      String text;
		      e.printStackTrace();
		    }
		  }
		  
		  public void loadConfiguration()
		  {
		    FileConfiguration file = YamlConfiguration.loadConfiguration(this.configfile);
		    for (String arena : file.getKeys(false))
		    {
		      ConfigurationSection section = file.getConfigurationSection(arena);
		      for (String block : section.getKeys(false))
		      {
		        ConfigurationSection blockSection = section.getConfigurationSection(block);
		        SignInfo si = new SignInfo(blockSection.getString("world"), blockSection.getInt("x"), blockSection.getInt("y"), blockSection.getInt("z"));
		        addSignInfo(si, arena);
		      }
		      modifySigns(arena);
		    }
		  }
		  
		  @SuppressWarnings("unused")
		public void saveConfiguration()
		  {
		    FileConfiguration file = new YamlConfiguration();
		    for (String arena : this.signs.keySet())
		    {
		      ConfigurationSection section = file.createSection(arena);
		      int i = 0;
		      for (SignInfo si : getSigns(arena))
		      {
		        ConfigurationSection blockSection = section.createSection(Integer.toString(i++));
		        blockSection.set("x", Integer.valueOf(si.getX()));
		        blockSection.set("y", Integer.valueOf(si.getY()));
		        blockSection.set("z", Integer.valueOf(si.getZ()));
		        blockSection.set("world", si.getWorldName());
		      }
		    }
		    try
		    {
			ConfigurationSection section;
		      int i;
		      file.save(this.configfile);
		    }
		    catch (IOException e) {}
		  }
}
