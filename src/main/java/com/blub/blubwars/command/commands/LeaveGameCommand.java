package com.blub.blubwars.command.commands;

import com.blub.blubwars.Blubwars;
import com.blub.blubwars.instance.Arena;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class LeaveGameCommand {

    public LeaveGameCommand(Player player, Blubwars blubwars){
        Arena arena = blubwars.getArenaManager().getArena(player);
        if (arena != null) {
            player.sendMessage(ChatColor.BLUE + "You have left the arena!");
            arena.removePlayer(player);
        } else {
            player.sendMessage(ChatColor.RED + "You are not in a arena!");
        }
    }
}
