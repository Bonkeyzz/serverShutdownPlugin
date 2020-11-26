package me.Bonkeyzz.shutdownPlugin.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import me.Bonkeyzz.shutdownPlugin.Main;


public class setShutdownTimerCountCommand implements CommandExecutor {

	public Main instance;
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		instance = me.Bonkeyzz.shutdownPlugin.Main.getInstance();
		if(args.length > 0) {
			if(sender instanceof Player) {
				Player player = (Player)sender;
				try {
					int timeUntilShutdown = Integer.parseInt(args[0]);
					if(player.isOp()) {
						instance.secondsUntilShutdown = timeUntilShutdown;
						instance.config.set("serverIdlePoweroff.timeUntilShutdown", timeUntilShutdown);
						instance.saveConfig();
						player.sendMessage(ChatColor.GREEN + "Auto-Shutdown timer count has been set to: " + timeUntilShutdown + " seconds!" + ChatColor.RESET);
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
					int timeUntilShutdown = Integer.parseInt(args[0]);
					instance.secondsUntilShutdown = timeUntilShutdown;
						instance.config.set("serverIdlePoweroff.timeUntilShutdown", timeUntilShutdown);
						instance.saveConfig();
						consoleSender.sendMessage(ChatColor.GREEN + "Auto-Shutdown timer count has been set to: " + timeUntilShutdown + " seconds!" + ChatColor.RESET);
				} catch(Exception e) {
					e.printStackTrace();
					consoleSender.sendMessage(ChatColor.RED + "There was an error executing this command!" + ChatColor.RESET);
					return false;
				}
			}
			} else {
				if(sender instanceof Player) {
					Player player = (Player)sender;
					player.sendMessage(ChatColor.GRAY + "Usage: /setShutdownIdleCount <seconds>" + ChatColor.RESET);
				} else if (sender instanceof ConsoleCommandSender) {
					ConsoleCommandSender cmdSender = (ConsoleCommandSender)sender;
					cmdSender.sendMessage(ChatColor.WHITE + "Usage: setShutdownIdleCount <seconds>" + ChatColor.RESET);
				}
			}
		return true;
	}

}
