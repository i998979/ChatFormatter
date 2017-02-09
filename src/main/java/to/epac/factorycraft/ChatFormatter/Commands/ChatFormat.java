package to.epac.factorycraft.ChatFormatter.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import to.epac.factorycraft.ChatFormatter.Main;

public class ChatFormat implements CommandExecutor {

	private Main plugin;

	public ChatFormat(Main pl) {
		plugin = pl;
	}

	public void HelpPage(CommandSender sender) {
		sender.sendMessage(
				ChatColor.GREEN + "----------" + ChatColor.AQUA + "ChatFormatter" + ChatColor.GREEN + "----------");
		sender.sendMessage(ChatColor.GOLD + "Author: " + ChatColor.AQUA + "i998979");
		sender.sendMessage(ChatColor.DARK_GREEN + "Commands:");
		sender.sendMessage(
				ChatColor.AQUA + "/chatformatter reload: " + ChatColor.LIGHT_PURPLE + "Reload configuration");
		sender.sendMessage(
				ChatColor.GREEN + "----------" + ChatColor.AQUA + "ChatFormatter" + ChatColor.GREEN + "----------");
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length == 0)
			HelpPage(sender);
		else {
			if (args[0].equalsIgnoreCase("reload")) {
				plugin.reloadConfig();
				sender.sendMessage(ChatColor.GREEN + "Configuration reloaded.");
			}

			else if (args[0].equalsIgnoreCase("opRedName")) {
				if (args[1].equalsIgnoreCase("true")) {
					plugin.getConfig().set("OpRedName", true);
					sender.sendMessage(ChatColor.GREEN + "Red name of Op is now " + args[1]);
				}
				if (args[1].equalsIgnoreCase("false")) {
					plugin.getConfig().set("OpRedName", false);
					sender.sendMessage(ChatColor.RED + "Red name of Op is now " + args[1]);
				}
				plugin.saveConfig();

			} else
				HelpPage(sender);
		}
		return false;
	}
}
