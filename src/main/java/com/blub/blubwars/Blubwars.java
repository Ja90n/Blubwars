package com.blub.blubwars;

import com.blub.blubwars.listener.*;
import com.blub.blubwars.command.BlubwarsCommand;
import com.blub.blubwars.command.BlubwarsTabCompleter;
import com.blub.blubwars.instance.Arena;
import com.blub.blubwars.manager.ArenaManager;
import com.blub.blubwars.manager.ConfigManager;
import com.blub.blubwars.utils.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class Blubwars extends JavaPlugin {

    private ArenaManager arenaManager;

    @Override
    public void onEnable() {

        // Setting up bStats
        int pluginId = 14655;
        Metrics metrics = new Metrics(this, pluginId);

        // Setup config
        ConfigManager.setupConfig(this);

        // Creating arenaManager
        arenaManager = new ArenaManager(this);

        // Register listeners
        Bukkit.getPluginManager().registerEvents(new GameListener(this), this);
        Bukkit.getPluginManager().registerEvents(new BlockListeners(this), this);
        Bukkit.getPluginManager().registerEvents(new ConnectListener(this), this);
        Bukkit.getPluginManager().registerEvents(new GuiListeners(this), this);
        Bukkit.getPluginManager().registerEvents(new ItemListeners(this), this);

        // Register command
        getCommand("blubwars").setExecutor(new BlubwarsCommand(this));
        getCommand("blubwars").setTabCompleter(new BlubwarsTabCompleter(this));

        // Enable message
        Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE + "Enabling the " + ChatColor.BOLD.toString() +
                ChatColor.LIGHT_PURPLE + "Blubwars" + ChatColor.RESET.toString() + ChatColor.BLUE + " plugin!");
    }

    @Override
    public void onDisable() {
        if (getArenaManager().getArenas() != null){
            for (Arena arena : getArenaManager().getArenas()){
                arena.reset();
            }
        }

        // Disable message
        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Disabled the " + ChatColor.BOLD.toString() +
                ChatColor.LIGHT_PURPLE + "Blubwars" + ChatColor.RESET.toString() + ChatColor.RED + " plugin!");
        Bukkit.getConsoleSender().sendMessage(ChatColor.LIGHT_PURPLE + "Thank you for playing!");
    }

    public ArenaManager getArenaManager() {
        return arenaManager;
    }
}