package com.blub.blubwars;

import com.blub.blubwars.commands.blubwarsCommand;
import com.blub.blubwars.commands.blubwarsTabCompleter;
import org.bukkit.plugin.java.JavaPlugin;

public final class Blubwars extends JavaPlugin {

    @Override
    public void onEnable() {
        // Get config

        // Get command
        getCommand("blubwars").setExecutor(new blubwarsCommand(this));
        getCommand("blubwars").setTabCompleter(new blubwarsTabCompleter(this));

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}