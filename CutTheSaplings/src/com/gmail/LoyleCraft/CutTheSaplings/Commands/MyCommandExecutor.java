package com.gmail.LoyleCraft.CutTheSaplings.Commands;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Vector;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.LoyleCraft.CutTheSaplings.CutTheSaplings;

public class MyCommandExecutor implements CommandExecutor{
	public CutTheSaplings plugin;
	private HashMap<String, BasicCommand> commands;
	
	  @SuppressWarnings({ "rawtypes", "unchecked" })
	public MyCommandExecutor(CutTheSaplings pl) {
	    this.plugin = pl;
	    this.commands = new HashMap();
	    loadCommands();
	}
	  
	private void loadCommands() {
		// Ici on les commandes (Et la class correspondante)
		this.commands.put("setcorner", new SetCorner(this.plugin));
		this.commands.put("setspawn", new SetSpawn(this.plugin));
		this.commands.put("setlobbyspawn", new SetLobbySpawn(this.plugin));
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	// Pour les commandes basiques (help), pour les commandes inconnues, et pour les commandes venant de la console
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		Player player = null;
		
		// On v�rifie que c'est bien un joueur r�el
		if ((sender instanceof Player)){
			player = (Player)sender;
		}
		else {
			sender.sendMessage("Vous devez �tre un joueur");
			return true;
		}
		
		// Pour la commande /cts et /cts help
		if (cmd.getName().equalsIgnoreCase("cts")) {
			if ((args == null) || (args.length < 1)) {
				player.sendMessage(ChatColor.YELLOW + "Plugin par LoyleCraft | version 1.0.0 | /cts help pour voir la liste des commandes");
				return true;
			}
			if (args[0].equalsIgnoreCase("help")) {
		        help(player);
		        return true;
			}
	      }
		
		// On v�rifie si la commande existe, si oui on execute la class correspondant � la commande
		String sub = args[0];
	    
	    Vector<String> l = new Vector();
	    l.addAll(Arrays.asList(args));
	    l.remove(0);
	    args = (String[])l.toArray(new String[0]);
	    if (!this.commands.containsKey(sub)) {
	    	player.sendMessage(ChatColor.RED + "Cette commande n'existe pas !");
	    	player.sendMessage(ChatColor.GOLD + "Taper /cts help pour avoir de l'aide");
	    	return true;
	    }
	    try {
	    	((BasicCommand)this.commands.get(sub)).onCommand(player, args);
	    }
	    catch (Exception e) {
	    	e.printStackTrace();
	        
	    	player.sendMessage(ChatColor.RED + "Une erreur est survenue pendant l'ex�cution de la commande. V�rifiez la console");
	    	player.sendMessage(ChatColor.BLUE + "Taper /cts help pour avoir de l'aide");
	    }
	    return true;
	}
	
	// Pour la commande help, boucle qui �crit toute les commandes du plugin
	public void help(Player p) {
		p.sendMessage("/cts <commande> <args>");
		for (BasicCommand v : this.commands.values()) {
			p.sendMessage(ChatColor.GRAY + "- " + v.help(p));
		}
	}
}
