package com.blub.blubwars.commands;

import com.blub.blubwars.Blubwars;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.List;

public class blubwarsTabCompleter implements TabCompleter {

    private Blubwars blubwars;
    public blubwarsTabCompleter(Blubwars blubwars) {
        this.blubwars = blubwars;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {

        return null;
    }
}
