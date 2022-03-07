package com.blub.blubwars.command.commands;

import com.blub.blubwars.Blubwars;
import com.blub.blubwars.GameState;
import com.blub.blubwars.instance.Arena;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ListAllAreansCommand {

    List<Arena> joinableArenas = new ArrayList<>();
    List<Arena> unJoinableArenas = new ArrayList<>();

    public ListAllAreansCommand(Player player, Blubwars blubwars){
        player.sendMessage(ChatColor.BLUE + "These are the avalible arenas:");
        for (Arena arena : blubwars.getArenaManager().getArenas()) {
            if (arena.getState().equals(GameState.RECRUITING) || arena.getState().equals(GameState.COUNTDOWN)){
                joinableArenas.add(arena);
            } else {
                unJoinableArenas.add(arena);
            }
            player.sendMessage(ChatColor.BLUE + "- " + arena.getId() + " (" + arena.getState().name() + ")");
        }
        if (!joinableArenas.isEmpty()){
            player.sendMessage(ChatColor.AQUA + "You can join the following arenas:");
            for (Arena arena : joinableArenas){
                player.sendMessage(ChatColor.AQUA + "- " + arena.getId() + ChatColor.GRAY + " (Players: " + arena.getPlayers().size() + ")");
            }
        }
        if (!unJoinableArenas.isEmpty()){
            player.sendMessage(ChatColor.RED + "The following arenas are live:");
            for (Arena arena : unJoinableArenas){
                player.sendMessage(ChatColor.RED + "- " + arena.getId() + ChatColor.GRAY + " (Players: " + arena.getPlayers().size() + ")");
            }
        }
    }
}
