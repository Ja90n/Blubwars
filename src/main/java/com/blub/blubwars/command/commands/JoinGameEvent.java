package com.blub.blubwars.command.commands;

import com.blub.blubwars.Blubwars;
import com.blub.blubwars.GameState;
import com.blub.blubwars.instance.Arena;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class JoinGameEvent {

    public JoinGameEvent(Player player, Blubwars blubwars,String[] args){
        Arena arena = blubwars.getArenaManager().getArena(player);
        if (arena != null) {
            arena.removePlayer(player);
        }
        try {
            int arenaId = Integer.parseInt(args[1]);
            if (arenaId >= 0 && arenaId < blubwars.getArenaManager().getArenas().size()) {
                if (blubwars.getArenaManager().getArena(arenaId).getState().equals(GameState.RECRUITING) || blubwars.getArenaManager().getArena(arenaId).getState().equals(GameState.COUNTDOWN)) {
                    if (arena.getCanJoin()) {
                        player.sendMessage(ChatColor.BLUE + "You are now playing in arena " + arena.getId() + "!");
                        arena.addPlayer(player);
                    } else {
                        player.sendMessage(ChatColor.RED + "You can't join right now. Map still loading!");
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "Game is already active!");
                }
            } else {
                player.sendMessage(ChatColor.RED + "The arena you specified does not exist!");
            }
        } catch (NumberFormatException e) {
            player.sendMessage(ChatColor.RED + "You need to fill in a number!");
        }
    }
}