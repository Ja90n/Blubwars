package com.blub.blubwars.commands;

import com.blub.blubwars.Blubwars;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class blubwarsCommand implements CommandExecutor {

    private Blubwars blubwars;
    public blubwarsCommand(Blubwars blubwars) {
        this.blubwars = blubwars;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {



        return false;
    }
}