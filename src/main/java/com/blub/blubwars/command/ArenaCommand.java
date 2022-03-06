package com.blub.blubwars.command;

import com.blub.blubwars.Blubwars;
import com.blub.blubwars.command.commandos.*;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ArenaCommand implements CommandExecutor {

    private Blubwars blubwars;
    private Player player;


    public ArenaCommand(Blubwars blubwars) {
        this.blubwars = blubwars;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        boolean bool = false;
        if (sender instanceof Player) {
            Player player = (Player) sender;
            this.player = player;
            bool = this.handleCommand(args);

        }
        //It wil be always false? Why??
        return bool;
    }

    private boolean handleCommand(String[] text) {
        GameWrapper gameWrapper = new GameWrapper(blubwars, player);
        if (text[0] == "list") {
            new GetAllArenasCommando(gameWrapper, text).Execute();

        }
        if (text[0] == "leave") {
            new DeletePlayerCommando(gameWrapper, text).Execute();
        }
        if (text[0] == "join") {

            new JoinCommand(gameWrapper, text).Execute();
        }
        if (text[0] == "team") {
            new TeamCommando(gameWrapper, text).Execute();

        }
        if (text[0] == "forcestop") {
            new StopCommando(gameWrapper, text).Execute();

        } else {
            player.sendMessage(ChatColor.RED + "Invalid usage");
        }

        return false;
    }


}
