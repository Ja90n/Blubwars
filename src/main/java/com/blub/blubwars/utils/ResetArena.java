package com.blub.blubwars.utils;

import com.blub.blubwars.Blubwars;
import com.blub.blubwars.instance.Arena;
import com.blub.blubwars.instance.Cuboid;
import com.blub.blubwars.manager.ConfigManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;


public class ResetArena{

    public ResetArena(Arena arena){

        Cuboid arenaCuboid = new Cuboid(
                ConfigManager.getPosition(arena,1),
                ConfigManager.getPosition(arena,2)
        );

        for (Block block : arenaCuboid.getBlocks()){
            if (block.getType().equals(Material.RED_WOOL) || block.getType().equals(Material.BLUE_WOOL) ||
                    block.getType().equals(Material.GREEN_WOOL) || block.getType().equals(Material.PINK_WOOL) ||
                    block.getType().equals(Material.RED_CONCRETE) || block.getType().equals(Material.GREEN_CONCRETE) ||
                    block.getType().equals(Material.BLUE_CONCRETE) || block.getType().equals(Material.PINK_CONCRETE) ||
                    block.getType().equals(Material.RED_STAINED_GLASS) || block.getType().equals(Material.BLUE_STAINED_GLASS) ||
                    block.getType().equals(Material.GREEN_STAINED_GLASS) || block.getType().equals(Material.PINK_STAINED_GLASS)
            ){
                block.setType(Material.AIR);
            } else if (block.getType().equals(Material.CHEST)){
                block.setType(Material.AIR);
                block.setType(Material.CHEST);
            }
        }
    }
}