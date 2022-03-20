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
                                e.getPlayer().sendMessage(ChatColor.RED + "You can not place this block here!");
                            }
                        }
                    }
                } catch (NullPointerException exception){}
                if (!arena.getArenaCuboid().contains(e.getBlock())){
                    e.setCancelled(true);
                    e.getPlayer().sendMessage(ChatColor.RED + "You can not place this block here!");
                }
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e){
        for (Arena arena : blubwars.getArenaManager().getArenas()){
            if (arena.getPlayers().contains(e.getPlayer().getUniqueId())){
                Block block = e.getBlock();
                if (!(block.getType().equals(Material.RED_WOOL) || block.getType().equals(Material.BLUE_WOOL) ||
                        block.getType().equals(Material.GREEN_WOOL) || block.getType().equals(Material.PINK_WOOL) ||
                        block.getType().equals(Material.RED_CONCRETE) || block.getType().equals(Material.GREEN_CONCRETE) ||
                        block.getType().equals(Material.BLUE_CONCRETE) || block.getType().equals(Material.PINK_CONCRETE) ||
                        block.getType().equals(Material.RED_STAINED_GLASS) || block.getType().equals(Material.BLUE_STAINED_GLASS) ||
                        block.getType().equals(Material.GREEN_STAINED_GLASS) || block.getType().equals(Material.PINK_STAINED_GLASS))){
                    e.getPlayer().sendMessage(ChatColor.RED + "You can not break this block!");
                    e.setCancelled(true);
                }
            }
        }
    }
}