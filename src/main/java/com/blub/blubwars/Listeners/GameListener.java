package com.blub.blubwars.Listeners;

import com.blub.blubwars.Blubwars;
import com.blub.blubwars.GameState;
import com.blub.blubwars.instance.Arena;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class GameListener implements Listener {

    private Blubwars blubwars;
    public GameListener(Blubwars blubwars) {
        this.blubwars = blubwars;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e){

        Arena arena = blubwars.getArenaManager().getArena(e.getPlayer());
        if (arena != null && arena.getState().equals(GameState.LIVE)){
            arena.getGame().addPoint(e.getPlayer());
        }
    }
}
