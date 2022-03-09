package com.blub.blubwars.command.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class HelpCommand {

    public HelpCommand(Player player){
        player.sendMessage(ChatColor.WHITE + "-----------------" + ChatColor.LIGHT_PURPLE + "=+=" + ChatColor.WHITE + "-----------------");
        player.sendMessage(ChatColor.AQUA + "/blubwars help: " + ChatColor.WHITE + "Gives you all the commands");
        player.sendMessage(ChatColor.RED + "/blubwars join <arena>: " + ChatColor.WHITE + "Makes you join a game");
        player.sendMessage(ChatColor.BLUE + "/blubwars leave: " + ChatColor.WHITE + "Makes you leave your game");
        player.sendMessage(ChatColor.YELLOW + "/blubwars list: " + ChatColor.WHITE + "Lists all arenas");
        player.sendMessage(ChatColor.YELLOW + "/blubwars team: " + ChatColor.WHITE + "Here you can switch teams");
        player.sendMessage(ChatColor.WHITE + "-----------------" + ChatColor.LIGHT_PURPLE + "=+=" + ChatColor.WHITE + "-----------------");
    }
}
