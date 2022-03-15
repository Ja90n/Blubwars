package com.blub.blubwars.manager;

import com.blub.blubwars.Blubwars;
import com.blub.blubwars.enums.Team;
import com.blub.blubwars.instance.Arena;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {

    private static FileConfiguration config;

    public static void setupConfig (Blubwars blubwars){
        ConfigManager.config = blubwars.getConfig();
        blubwars.saveDefaultConfig();
    }

    public static int getRequiredPlayers() { return config.getInt("required-players");}
    public static int getCountdownSeconds() { return config.getInt("countdown-seconds");}
    public static int getCatLives() { return config.getInt("cat-lives");}

    public static Location getPosition(Arena arena,int position){
        return new Location(
                arena.getWorld(),
                config.getDouble("arenas." + arena.getId() + ".pos" + position + ".x"),
                config.getDouble("arenas." + arena.getId() + ".pos" + position + ".y"),
                config.getDouble("arenas." + arena.getId() + ".pos" + position + ".z"));
    }

    public static Location getPosition(Arena arena,int position,Team team){
        return new Location(
                arena.getWorld(),
                config.getDouble("arenas." + arena.getId() + "." + team.getTeamName() + ".pos" + position + ".x"),
                config.getDouble("arenas." + arena.getId() + "." + team.getTeamName() + ".pos" + position + ".y"),
                config.getDouble("arenas." + arena.getId() + "." + team.getTeamName() + ".pos" + position + ".z"));
    }

    public static Location getLobbySpawn(){
        return new Location(
                Bukkit.getWorld(config.getString("lobby-spawn.world")),
                config.getDouble("lobby-spawn.x"),
                config.getDouble("lobby-spawn.y"),
                config.getDouble("lobby-spawn.z"),
                (float) config.getDouble("lobby-spawn.yaw"),
                (float) config.getDouble("lobby-spawn.pitch"));
    }
}
