package com.blub.blubwars.manager;

import com.blub.blubwars.instance.Arena;
import com.blub.blubwars.Blubwars;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ArenaManager {

    private List<Arena> arenas = new ArrayList<>();


    public ArenaManager (Blubwars blubwars){
        try {
            FileConfiguration config = blubwars.getConfig();
            for (String str : config.getConfigurationSection("arenas.").getKeys(false)){
                World world = Bukkit.createWorld(new WorldCreator(config.getString("arenas." + str + ".lobby.world")));
                world.setAutoSave(false);

                arenas.add(new Arena(blubwars, Integer.parseInt(str), new Location(
                        Bukkit.getWorld(config.getString("arenas." + str + ".lobby.world")),
                        config.getDouble("arenas." + str + ".lobby.x"),
                        config.getDouble("arenas." + str + ".lobby.y"),
                        config.getDouble("arenas." + str + ".lobby.z"),
                        (float) config.getDouble("arenas." + str + ".lobby.yaw"),
                        (float) config.getDouble("arenas." + str + ".lobby.pitch"))));
            }
        } catch (NullPointerException e){
            Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "The config file is not set up correctly");
        }
    }

    public List<Arena> getArenas() { return arenas; }

    public Arena getArena(Player player){
        for (Arena arena : arenas){
            if (arena.getPlayers().contains(player.getUniqueId())){
                return arena;
            }
        }
        return null;
    }

    public Arena getArena(int id){
        for (Arena arena : arenas){
            if (arena.getId() == id){
                return arena;
            }
        }
        return null;
    }

    public Arena getArena(World world){
        for (Arena arena : arenas){
            if (arena.getWorld().getName().equals(world.getName())){
                return arena;
            }
        }
        return null;
    }
}
