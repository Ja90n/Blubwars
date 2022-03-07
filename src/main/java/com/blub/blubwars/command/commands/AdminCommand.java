package com.blub.blubwars.command.commands;

import com.blub.blubwars.Blubwars;
import com.blub.blubwars.command.commands.admincommands.AdminListAllAreansCommand;
import com.blub.blubwars.command.commands.admincommands.AdminStartCommand;
import com.blub.blubwars.command.commands.admincommands.AdminStopCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class AdminCommand {
    public AdminCommand(Player player, Blubwars blubwars, String[] args){
        if (player.hasPermission("blubwars.admin")){
            switch (args[1]){
                case "stop":
                    new AdminStopCommand(player,args,blubwars);
                    break;
                case "start":
                    new AdminStartCommand(player,args,blubwars);
                    break;
                case "list":
                    new AdminListAllAreansCommand(player,blubwars);
                    break;
                default:
                    AdminHelp(player);
            }
        } else {
            player.sendMessage(ChatColor.RED + "You do not have permission to execute this command!");
        }
    }

    private void AdminHelp(Player player){
        player.sendMessage(ChatColor.WHITE + "-----------------" + ChatColor.LIGHT_PURPLE + "=+=" + ChatColor.WHITE
                + "-----------------");
        player.sendMessage(ChatColor.AQUA + "/blubwars admin help: " + ChatColor.WHITE
                + "Gives you all the commands you use as a admin");
        player.sendMessage(ChatColor.RED + "/blubwars admin start <arena>: "
                + ChatColor.WHITE + "Makes you start a game");
        player.sendMessage(ChatColor.BLUE + "/blubwars admin stop <arena>: " + ChatColor.WHITE
                + "Makes you stop a game");
        player.sendMessage(ChatColor.YELLOW + "/blubwars admin list: " + ChatColor.WHITE
                + "Lists all arenas and players");
        player.sendMessage(ChatColor.WHITE + "-----------------" + ChatColor.LIGHT_PURPLE
                + "=+=" + ChatColor.WHITE + "-----------------");

    }
}
