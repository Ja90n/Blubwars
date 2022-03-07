package com.blub.blubwars.command;

import com.blub.blubwars.Blubwars;
import com.blub.blubwars.command.commands.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MainCommand implements CommandExecutor {

    private Blubwars blubwars;

    public MainCommand(Blubwars blubwars) {
        this.blubwars = blubwars;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            switch (args.length) {
                case 1:
                    switch (args[0]) {
                        case "list":
                            new ListAllAreansCommand(player,blubwars);
                            break;
                        case "leave":
                            new LeaveGameCommand(player,blubwars);
                            break;
                        default:
                            new HelpCommand(player);
                            break;
                    }
                    break;
                case 2:
                    switch (args[0]) {
                        case "admin":
                            new AdminCommand(player,blubwars,args);
                            break;
                        case "join":
                            new JoinGameEvent(player,blubwars,args);
                            break;
                        default:
                            new HelpCommand(player);
                            break;
                    }
                    break;
                case 3:
                    if (args[0].equals("admin")){
                        new AdminCommand(player,blubwars,args);
                        break;
                    }
                default:
                    new HelpCommand(player);
                    break;
            }
        } else {
            sender.sendMessage(ChatColor.RED + "You can not execute this command!");
        }
        return false;
    }
}
