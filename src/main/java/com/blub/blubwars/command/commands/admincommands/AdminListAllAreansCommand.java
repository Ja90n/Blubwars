package com.blub.blubwars.command.commands.admincommands;

import com.blub.blubwars.Blubwars;
import com.blub.blubwars.instance.Arena;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.UUID;

public class AdminListAllAreansCommand {

    public AdminListAllAreansCommand(Player player, Blubwars blubwars){
        player.sendMessage(ChatColor.BLUE + "These are the avalible arenas:");
        for (Arena arena : blubwars.getArenaManager().getArenas()){
            player.sendMessage("- " + arena.getId() + ChatColor.GRAY + "(" + arena.getState() + ")");
            if (!arena.getPlayers().isEmpty()){
                player.sendMessage(ChatColor.BLUE + "   Players " + ChatColor.GRAY + "(" + arena.getPlayers().size() + ")" + ChatColor.BLUE + ":");
                for (UUID targetUUID : arena.getPlayers()){
                    Player target = Bukkit.getPlayer(targetUUID);
                    player.sendMessage(ChatColor.BLUE + "   -" + target.getDisplayName());
                }
            } else {
                player.sendMessage(ChatColor.RED + "This arena is empty!");
            }
        }
    }
}
