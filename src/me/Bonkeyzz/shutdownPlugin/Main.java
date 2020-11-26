package me.Bonkeyzz.shutdownPlugin;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import me.Bonkeyzz.shutdownPlugin.Commands.enableTimedShutdownCommand;
import me.Bonkeyzz.shutdownPlugin.Commands.setShutdownTimerCountCommand;

public class Main extends JavaPlugin {
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";
	
	public int secondsUntilShutdown = 378;
	public boolean shutdownPluginEnabled = true;
	
	public FileConfiguration config;
	private static Main instance;
	
	public static Main getInstance() {
		return instance;
	}
	@Override
	public void onEnable() {
		instance = this;
		config = getConfig();
		if(!config.isSet("serverIdlePoweroff.enabled") && !config.isSet("serverIdlePoweroff.timeUntilShutdown")) {
			config.set("serverIdlePoweroff.enabled", true);
			config.set("serverIdlePoweroff.timeUntilShutdown", 378);
			saveConfig();
		}
		else {
			shutdownPluginEnabled = (boolean)config.get("serverIdlePoweroff.enabled");
			secondsUntilShutdown = (int)config.get("serverIdlePoweroff.timeUntilShutdown");
		}
		Bukkit.getLogger().info(ANSI_GREEN + "[serverIdlePoweroff] Plugin loaded!" + ANSI_RESET);
		Bukkit.getLogger().info("[serverIdlePoweroff] Status: " + (shutdownPluginEnabled ? "Enabled" : "Disabled" ));
		
		getCommand("enableautoshutdown").setExecutor((CommandExecutor) new enableTimedShutdownCommand());
		getCommand("setshutdowntimer").setExecutor((CommandExecutor) new setShutdownTimerCountCommand());
		Thread mainThread = new Thread() {
			  public void run() {
				  boolean displayedMessage = false;
				  
				  Bukkit.getLogger().info("[serverIdlePoweroff] Default shutdown time: " + secondsUntilShutdown + " seconds.");
			   while(true) {
				   if(shutdownPluginEnabled) {
				   try {
					   if(Bukkit.getOnlinePlayers().length == 0) {
						   displayedMessage = false;
						   Bukkit.getLogger().warning(ANSI_YELLOW + "No players online! Starting shutdown timer..." + ANSI_RESET);
						   long createdMillis = System.currentTimeMillis();
						   while(Bukkit.getOnlinePlayers().length == 0) {
							   long nowMillis = System.currentTimeMillis();
							   int elapsedSecs = (int)((nowMillis - createdMillis) / 1000);
							   if(elapsedSecs == (secondsUntilShutdown - 10)) {
								   Bukkit.getLogger().warning(ANSI_YELLOW + "Server will shutdown in 10 seconds cause no player is online." + ANSI_RESET);
							   } else if(elapsedSecs == secondsUntilShutdown) {
								   Bukkit.getLogger().warning(ANSI_RED + "Shutting down server..." + ANSI_RESET);
								   getServer().shutdown();
							   }
							   Thread.sleep(1000);
						   }
					   }
					   else {
						   if(!displayedMessage) {
							   Bukkit.getLogger().info(ANSI_YELLOW + "Player connected! Stopping shutdown timer..." + ANSI_RESET);
							   displayedMessage = true;
						   }
					   }
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			    }
			   }
			  }
			};
			mainThread.start();
	}
	@Override
	public void onDisable() {
		Bukkit.getLogger().info("Saving config...");
		config.set("serverIdlePoweroff.enabled", shutdownPluginEnabled);
		config.set("serverIdlePoweroff.timeUntilShutdown", secondsUntilShutdown);
		saveConfig();
	}
}
