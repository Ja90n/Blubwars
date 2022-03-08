package com.blub.blubwars.instance;

import com.blub.blubwars.GameState;
import com.blub.blubwars.manager.ConfigManager;
import com.blub.blubwars.team.Team;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.entity.Cat;
import org.bukkit.entity.EntityType;

import java.util.HashMap;
import java.util.UUID;

public class Game {

    private HashMap<Cat, Integer> catLives;
    private Arena arena;
    private HashMap<UUID, Integer> points;

    public Game(Arena arena) {
        this.arena = arena;
        points = new HashMap<>();
        catLives = new HashMap<>();
    }

    public void start() {
        arena.setState(GameState.LIVE);
        arena.sendMessage(ChatColor.BLUE + "Game has started!");
        for (UUID uuid : arena.getPlayers()) {
            points.put(uuid, 0);
            Bukkit.getPlayer(uuid).closeInventory();
        }
        for (Team team : Team.values()){
            if (team != null){
                if (arena.getTeamCount(team) > 0){
                    spawnCat(team);
                }
            }
        }
    }

    public void removeCatLive(Cat cat,Team team){
        catLives.put(cat,catLives.get(cat)-1);
        if (catLives.get(cat) > 0){
            spawnCat(team);
        } else {
            catLives.remove(cat);
            if (catLives.size() < 2){
                for (Cat target : catLives.keySet()){
                    arena.sendTitle(ChatColor.AQUA + "Team " + target.getCustomName().split(" ")[0],ChatColor.AQUA + "has won the game!");
                    arena.sendMessage(target.getCustomName().split(" ")[0] + ChatColor.AQUA + " has won!");
                }
                arena.sendMessage(ChatColor.LIGHT_PURPLE + "Thank you for playing - Blubdev");
                arena.reset();
            }
        }
    }

    public void spawnCat(Team team){
        Cat cat = (Cat) arena.getWorld().spawnEntity(arena.getTeamSpawn(team), EntityType.CAT);
        if (catLives.containsKey(cat)){
            catLives.put(cat,catLives.get(cat)-1);
        } else {
            catLives.put(cat, ConfigManager.getCatLives());
        }
        cat.setCustomName(team.getDisplay() + " cat, lives: " + catLives.get(cat));
        DyeColor dyeColor = DyeColor.WHITE;
        if (team.equals(Team.RED)){
            dyeColor = DyeColor.RED;
        } else if (team.equals(Team.BLUE)){
            dyeColor = DyeColor.BLUE;
        } else if (team.equals(Team.GREEN)){
            dyeColor = DyeColor.GREEN;
        } else if (team.equals(Team.PINK)){
            dyeColor = DyeColor.PINK;
        }
        cat.setCollarColor(dyeColor);
    }



    public HashMap<Cat,Integer> getCatLives() { return catLives; }
    public void clearCatLives() {  catLives.clear(); }
}
