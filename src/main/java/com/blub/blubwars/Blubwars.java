package com.blub.blubwars;

import com.blub.blubwars.manager.ArenaManager;
import com.blub.blubwars.manager.configManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class Blubwars extends JavaPlugin {

    private ArenaManager arenaManager;

    @Override
    public void onEnable() {
        // Setup config
        configManager.setupConfig(this);

        //
         arenaManager = new ArenaManager(this);

        // Enable message
        Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE + "Enabling the " + ChatColor.BOLD.toString() + ChatColor.LIGHT_PURPLE + "Blub" + ChatColor.BOLD.toString() + ChatColor.WHITE + "wars" + ChatColor.RESET.toString() + ChatColor.BLUE + " plugin!");

    }

    @Override
    public void onDisable() {
        // Disable message
        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Disabled the " + ChatColor.BOLD.toString() + ChatColor.LIGHT_PURPLE + "Blub" + ChatColor.BOLD.toString() + ChatColor.WHITE + "wars" + ChatColor.RESET.toString() + ChatColor.RED + " plugin!");
        Bukkit.getConsoleSender().sendMessage(ChatColor.LIGHT_PURPLE + "Thank you for playing!");
    }

    public ArenaManager getArenaManager() { return arenaManager; }
}