package com.blub.blubwars.listener;

import com.blub.blubwars.Blubwars;
import com.blub.blubwars.enums.GameState;
import com.blub.blubwars.instance.Arena;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockListeners implements Listener {

    private Blubwars blubwars;

    public BlockListeners(Blubwars blubwars) {
        this.blubwars = blubwars;
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e){
        for (Arena arena : blubwars.getArenaManager().getArenas()){
            if (e.getPlayer().getWorld().equals(arena.getWorld())){
                if (arena.getState().equals(GameState.LIVE)){
                    if (!(e.getBlock().getType().equals(Material.RED_WOOL) ||
                            e.getBlock().getType().equals(Material.BLUE_WOOL) ||
                            e.getBlock().getType().equals(Material.GREEN_WOOL) ||
                            e.getBlock().getType().equals(Material.PINK_WOOL)
                    )){
                        e.setCancelled(true);
                        e.getPlayer().sendMessage(ChatColor.RED + "You can not place this block!");
                    }
                }
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e){
        for (Arena arena : blubwars.getArenaManager().getArenas()){
            if (e.getPlayer().getWorld().equals(arena.getWorld())){
                if (arena.getState().equals(GameState.LIVE)){
                    if (!(e.getBlock().getType().equals(Material.RED_WOOL) ||
                            e.getBlock().getType().equals(Material.BLUE_WOOL) ||
                            e.getBlock().getType().equals(Material.GREEN_WOOL) ||
                            e.getBlock().getType().equals(Material.PINK_WOOL)
                    )){
                        e.setCancelled(true);
                        e.getPlayer().sendMessage(ChatColor.RED + "You can not break this block!");
                    }
                }
            }
        }
    }
}
