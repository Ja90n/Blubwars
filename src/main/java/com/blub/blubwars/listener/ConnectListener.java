package com.blub.blubwars.listener;

import com.blub.blubwars.Blubwars;
import com.blub.blubwars.instance.Arena;
import com.blub.blubwars.manager.ConfigManager;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ConnectListener implements Listener {

    private Blubwars blubwars;

    public ConnectListener(Blubwars blubwars) {
        this.blubwars = blubwars;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        e.getPlayer().teleport(ConfigManager.getLobbySpawn());
        e.setJoinMessage(e.getPlayer().getDisplayName() + ChatColor.LIGHT_PURPLE + " has joined the server!");
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        Arena arena = blubwars.getArenaManager().getArena(e.getPlayer());
        if (arena != null){
            arena.removePlayer(e.getPlayer());
        }
        e.getPlayer().setInvulnerable(false);
        e.getPlayer().setInvisible(false);
        e.setQuitMessage(e.getPlayer().getDisplayName() + ChatColor.LIGHT_PURPLE + " has left the server!");
    }
}
