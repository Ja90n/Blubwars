package com.blub.blubwars.instance;

import com.blub.blubwars.GameState;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class Game {

    private Arena arena;
    private HashMap<UUID, Integer> points;

    public Game(Arena arena){
        this.arena = arena;
        points = new HashMap<>();
    }

    public void start(){
        arena.setState(GameState.LIVE);
        arena.sendMessage(ChatColor.BLUE + "Game has started!");

        for (UUID uuid : arena.getPlayers()){
            points.put(uuid, 0);
        }
    }

    public void addPoint(Player player){
        int playerPoints = points.get(player.getUniqueId()) + 1;
        if (playerPoints == 20){
            arena.sendMessage(ChatColor.LIGHT_PURPLE + player.getName() + " has won! :)");
            arena.reset(true);
        } else {
            player.sendMessage(ChatColor.BLUE + "+1 point!");
            points.replace(player.getUniqueId(), playerPoints);
        }
    }
}
