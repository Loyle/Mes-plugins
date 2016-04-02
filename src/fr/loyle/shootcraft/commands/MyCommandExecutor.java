package fr.loyle.shootcraft.commands;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Vector;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.loyle.shootcraft.ShootCraft;

public class MyCommandExecutor implements CommandExecutor{
	private ShootCraft plugin;
	private HashMap<String, BasicCommand> commands;
	
	public MyCommandExecutor(ShootCraft pl) {
	    this.plugin = pl;
	    this.commands = new HashMap<>();
	    loadCommands();
	}
	  
	private void loadCommands() {
		this.commands.put("setlobby", new SetLobbyCommand(this.plugin));
		this.commands.put("addspawn", new AddSpawnCommand(this.plugin));
	}

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
		if (cmd.getName().equalsIgnoreCase("shootcraft")) {
			if ((args == null) || (args.length < 1)) {
				player.sendMessage(ChatColor.YELLOW + "Plugin by Loyle | version 1.0.0 | /shootcraft help for see all commands");
				return true;
			}
			if (args[0].equalsIgnoreCase("help")) {
		        help(player);
		        return true;
			}
	      }
	      String sub = args[0];
	      
	      Vector<String> l = new Vector<>();
	      l.addAll(Arrays.asList(args));
	      l.remove(0);
	      args = (String[])l.toArray(new String[0]);
	      if (!this.commands.containsKey(sub))
	      {
	        player.sendMessage(ChatColor.RED + "Command dosent exist.");
	        player.sendMessage(ChatColor.GOLD + "Type /shootcraft help for help");
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
	        player.sendMessage(ChatColor.BLUE + "Type /shootcraft help for help");
	      }
	      return true;
	}
	public void help(Player p) {
		p.sendMessage("/shootcraft <command> <args>");
		for (BasicCommand v : this.commands.values()) {
			p.sendMessage(ChatColor.GRAY + "- " + v.help(p));
		}
	}
}
