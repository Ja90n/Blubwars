package com.blub.blubwars.Listeners;

import com.blub.blubwars.Blubwars;
import com.blub.blubwars.instance.Arena;
import com.blub.blubwars.manager.configManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ConnectListener implements Listener {

    private Blubwars blubwars;
    public ConnectListener(Blubwars blubwars) {
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){

        e.getPlayer().teleport(configManager.getLobbySpawn());

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        Arena arena = blubwars.getArenaManager().getArena(e.getPlayer());
        if (arena != null){
            arena.removePlayer(e.getPlayer());
        }
    }
}
