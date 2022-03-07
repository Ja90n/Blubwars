package com.blub.blubwars.command.commands.admincommands;

import com.blub.blubwars.Blubwars;
import com.blub.blubwars.GameState;
import com.blub.blubwars.instance.Arena;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class AdminStopCommand {

    public AdminStopCommand(Player player, String id, Blubwars blubwars){
        try {
            int arenaId = Integer.parseInt(id);
            Arena arena = blubwars.getArenaManager().getArena(arenaId);
            if (arenaId >= 0 && arenaId < blubwars.getArenaManager().getArenas().size()) {
                if (arena.getState().equals(GameState.RECRUITING) || arena.getState().equals(GameState.COUNTDOWN)) {
                    player.sendMessage(ChatColor.RED + "Game is not active!");
                } else {
                    arena.reset();
                    player.sendMessage(ChatColor.BLUE + "You have stopped the game!");
                }
            } else {
                player.sendMessage(ChatColor.RED + "The arena you specified does not exist!");
            }
        } catch (NumberFormatException e) {
            player.sendMessage(ChatColor.RED + "You need to fill in a number!");
        }
    }
}
