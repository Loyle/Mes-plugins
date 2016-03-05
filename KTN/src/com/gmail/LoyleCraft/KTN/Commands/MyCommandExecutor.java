package com.gmail.LoyleCraft.KTN.Commands;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Vector;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.LoyleCraft.KTN.KTN;

public class MyCommandExecutor implements CommandExecutor{
	public KTN plugin;
	private HashMap<String, BasicCommand> commands;
	
	  @SuppressWarnings({ "rawtypes", "unchecked" })
	public MyCommandExecutor(KTN pl) {
	    this.plugin = pl;
	    this.commands = new HashMap();
	    loadCommands();
	}
	  
	private void loadCommands() {
		// Ici on les commandes (Et la class correspondante)
		this.commands.put("setlobby", new SetLobby(this.plugin));
		this.commands.put("start", new Start(this.plugin));
		this.commands.put("jointeam", new JoinTeam(this.plugin));
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	// Pour les commandes basiques (help), pour les commandes inconnues, et pour les commandes venant de la console
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		Player player = null;
		
		// On vérifie que c'est bien un joueur réel
		if ((sender instanceof Player)){
			player = (Player)sender;
		}
		else {
			sender.sendMessage("Vous devez être un joueur");
			return true;
		}
		
		// Pour la commande /cts et /cts help
		if (cmd.getName().equalsIgnoreCase("ktn")) {
			if ((args == null) || (args.length < 1)) {
				player.sendMessage(ChatColor.GOLD + "Plugin par LoyleCraft | version 1.0.0 | /ktn help pour voir la liste des commandes");
				return true;
			}
			if (args[0].equalsIgnoreCase("help")) {
		        help(player);
		        return true;
			}
	      }
		
		// On vérifie si la commande existe, si oui on execute la class correspondant à la commande
		String sub = args[0];
	    
	    Vector<String> l = new Vector();
	    l.addAll(Arrays.asList(args));
	    l.remove(0);
	    args = (String[])l.toArray(new String[0]);
	    if (!this.commands.containsKey(sub)) {
	    	player.sendMessage(ChatColor.RED + "Cette commande n'existe pas !");
	    	player.sendMessage(ChatColor.GOLD + "Taper /ktn help pour avoir de l'aide");
	    	return true;
	    }
	    try {
	    	((BasicCommand)this.commands.get(sub)).onCommand(player, args);
	    }
	    catch (Exception e) {
	    	e.printStackTrace();
	        
	    	player.sendMessage(ChatColor.RED + "Une erreur est survenue pendant l'exécution de la commande. Vérifiez la console");
	    	player.sendMessage(ChatColor.BLUE + "Taper /ktn help pour avoir de l'aide");
	    }
	    return true;
	}
	
	// Pour la commande help, boucle qui écrit toute les commandes du plugin
	public void help(Player p) {
		p.sendMessage("/ktn <commande> <args>");
		for (BasicCommand v : this.commands.values()) {
			p.sendMessage(ChatColor.GRAY + "- " + v.help(p));
		}
	}
}
