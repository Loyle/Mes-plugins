package com.gmail.LoyleCraft.KTP2135.Commands;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Vector;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.LoyleCraft.KTP2135.KTP2135;

public class MyCommandExecutor implements CommandExecutor{
	
	  public KTP2135 plugin;
	  private HashMap<String, BasicCommand> commands;
	
	  @SuppressWarnings({ "rawtypes", "unchecked" })
	public MyCommandExecutor(KTP2135 pl) {
	    this.plugin = pl;
	    this.commands = new HashMap();
	    loadCommands();
	  }
	  
	  private void loadCommands() {
		  this.commands.put("create", new CreateCommand(this.plugin));
		  this.commands.put("setmin", new SetMinCommand(this.plugin));
		  this.commands.put("setmax", new SetMaxCommand(this.plugin));
		  this.commands.put("setlobbyname", new SetLobbyNameCommand(this.plugin));
		  this.commands.put("setlobbyspawn", new SetLobbySpawnCommand(this.plugin));
		  this.commands.put("setspawnarena", new SetSpawnArenaCommand(this.plugin));
		  this.commands.put("leave", new LeaveCommand(this.plugin));
		  this.commands.put("info", new InfoCommand(this.plugin));
		  this.commands.put("delete", new DeleteCommand(this.plugin));
		  this.commands.put("join", new JoinCommand(this.plugin));
		  this.commands.put("setcountdown", new SetCountDownCommand(this.plugin));
	  }

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		Player player = null;
		if ((sender instanceof Player)){
			player = (Player)sender;
		}
		else {
			sender.sendMessage("You need to be a player !");
			return true;
		}
		if (cmd.getName().equalsIgnoreCase("ktp2135")) {
			if ((args == null) || (args.length < 1)) {
				player.sendMessage(ChatColor.YELLOW + "Plugin by LoyleCraft | version 1.0.0 | /ktp2135 help for see all commands");
				return true;
			}
			if (args[0].equalsIgnoreCase("help")) {
		        help(player);
		        return true;
			}
	      }
	      String sub = args[0];
	      
	      Vector<String> l = new Vector();
	      l.addAll(Arrays.asList(args));
	      l.remove(0);
	      args = (String[])l.toArray(new String[0]);
	      if (!this.commands.containsKey(sub))
	      {
	        player.sendMessage(ChatColor.RED + "Command dosent exist.");
	        player.sendMessage(ChatColor.GOLD + "Type /ktp2135 help for help");
	        return true;
	      }
	      try
	      {
	        ((BasicCommand)this.commands.get(sub)).onCommand(player, args);
	      }
	      catch (Exception e)
	      {
	        e.printStackTrace();
	        
	        player.sendMessage(ChatColor.RED + "An error occurred while executing the command. Check the console");
	        player.sendMessage(ChatColor.BLUE + "Type /ktp2135 help for help");
	      }
	      return true;
	}
	  public void help(Player p)
	  {
	    p.sendMessage("/ktp2135 <command> <args>");
	    for (BasicCommand v : this.commands.values()) {
	      p.sendMessage(ChatColor.GRAY + "- " + v.help(p));
	    }
	  }
}
