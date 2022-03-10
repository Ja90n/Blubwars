package com.blub.blubwars;

import com.blub.blubwars.instance.Arena;
import com.blub.blubwars.instance.Cuboid;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;


public class ResetArena{

    public ResetArena(Arena arena, Blubwars blubwars){

        Location pos1 = new Location( arena.getWorld(),
                blubwars.getConfig().getDouble("arenas." + arena.getId() + ".pos1.x"),
                blubwars.getConfig().getDouble("arenas." + arena.getId() + ".pos1.y"),
                blubwars.getConfig().getDouble("arenas." + arena.getId() + ".pos1.z")
        );
        Location pos2 = new Location( arena.getWorld(),
                blubwars.getConfig().getDouble("arenas." + arena.getId() + ".pos2.x"),
                blubwars.getConfig().getDouble("arenas." + arena.getId() + ".pos2.y"),
                blubwars.getConfig().getDouble("arenas." + arena.getId() + ".pos2.z")
        );

        Cuboid arenaCuboid = new Cuboid(pos1, pos2);

        for (Block block : arenaCuboid.getBlocks()){
            if (block.getType().equals(Material.RED_WOOL) || block.getType().equals(Material.BLUE_WOOL) ||
                    block.getType().equals(Material.GREEN_WOOL) || block.getType().equals(Material.PINK_WOOL)){
                block.setType(Material.AIR);
            }
        }

    }
}
