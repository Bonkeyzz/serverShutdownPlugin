package me.Bonkeyzz.shutdownPlugin.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class enableTimedShutdownCommand implements CommandExecutor {
	public me.Bonkeyzz.shutdownPlugin.Main instance;

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		instance = me.Bonkeyzz.shutdownPlugin.Main.getInstance();
		if(args.length > 0) {
		if(sender instanceof Player) {
			Player player = (Player)sender;
			try {
				boolean enabled = Boolean.parseBoolean(args[0]);
				if(player.isOp()) {
					instance.shutdownPluginEnabled = enabled;
					instance.config.set("serverIdlePoweroff.enabled", enabled);
					instance.saveConfig();
					player.sendMessage(ChatColor.GREEN + "Auto-Shutdown on idle has been " + (enabled ? "enabled!" : "disabled!") + ChatColor.RESET);
					player.sendMessage(ChatColor.RED + "For the changes to take effect please do /reload or restart the server." + ChatColor.RESET);
				} else {
					player.sendMessage(ChatColor.RED + "You don't have permission to execute this command!" + ChatColor.RESET);
				}
			} catch(Exception e) {
				e.printStackTrace();
				player.sendMessage(ChatColor.RED + "There was an error executing this command!" + ChatColor.RESET);
			}
		}
		else if (sender instanceof ConsoleCommandSender) {
			ConsoleCommandSender consoleSender = (ConsoleCommandSender)sender;
			try {
				boolean enabled = Boolean.parseBoolean(args[0]);
					instance.shutdownPluginEnabled = enabled;
					instance.config.set("serverIdlePoweroff.enabled", enabled);
					instance.saveConfig();
					consoleSender.sendMessage(ChatColor.GREEN + "Auto-Shutdown on idle has been " + (enabled ? "enabled!" : "disabled!") + ChatColor.RESET);
					consoleSender.sendMessage(ChatColor.RED + "For the changes to take effect please do /reload or restart the server." + ChatColor.RESET);
			} catch(Exception e) {
				e.printStackTrace();
				consoleSender.sendMessage(ChatColor.RED + "There was an error executing this command!" + ChatColor.RESET);
				return false;
			}
		}
		} else {
			if(sender instanceof Player) {
				Player player = (Player)sender;
				player.sendMessage(ChatColor.GRAY + "Usage: /enableAutoShutdown <true/false>" + ChatColor.RESET);
			} else if (sender instanceof ConsoleCommandSender) {
				ConsoleCommandSender cmdSender = (ConsoleCommandSender)sender;
				cmdSender.sendMessage(ChatColor.WHITE + "Usage: enableAutoShutdown <true/false>" + ChatColor.RESET);
			}
		}
		return true;
	}

}
