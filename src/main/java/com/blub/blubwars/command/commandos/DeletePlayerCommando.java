package com.blub.blubwars.command.commandos;

import com.blub.blubwars.command.GameWrapper;
import com.blub.blubwars.instance.Arena;
import org.bukkit.ChatColor;

public class DeletePlayerCommando extends Commando{

    public DeletePlayerCommando(GameWrapper gameWrapper, String[] textCommandos) {
        super(gameWrapper, textCommandos);
    }

    @Override
    public void Execute() {

        Arena arena = blubwars.getArenaManager().getArena(player);
        if (arena == null) {
            sendMessage(ChatColor.RED + "You are not in a arena");
        } else {
            sendMessage(ChatColor.BLUE + "You have left the arena!");
            arena.removePlayer(player);
        }
    }
}
