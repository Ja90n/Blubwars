package com.blub.blubwars.listener;

import com.blub.blubwars.Blubwars;
import com.blub.blubwars.enums.GameState;
import com.blub.blubwars.instance.Arena;
import com.blub.blubwars.instance.Cuboid;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
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
            if (arena.getPlayers().contains(e.getPlayer().getUniqueId())){
                try {
                    for (Cuboid cuboid : arena.getGame().getNoPlacingCuboids().getCuboids().keySet()) {
                        for (Block block : cuboid.getBlocks()) {
                            if (block.getLocation().equals(e.getBlock().getLocation())) {
                                e.setCancelled(true);
                                e.getPlayer().sendMessage(ChatColor.RED + "You can not place this block!");
                            }
                        }
                    }
                } catch (NullPointerException exception){}
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e){
        for (Arena arena : blubwars.getArenaManager().getArenas()){
            if (arena.getPlayers().contains(e.getPlayer().getUniqueId())){
                if (!(e.getBlock().getType().equals(Material.RED_WOOL) ||
                        e.getBlock().getType().equals(Material.BLUE_WOOL) ||
                        e.getBlock().getType().equals(Material.GREEN_WOOL) ||
                        e.getBlock().getType().equals(Material.PINK_WOOL))){
                    e.getPlayer().sendMessage(ChatColor.RED + "You can not break this block!");
                    e.setCancelled(true);
                }
            }
        }
    }
}