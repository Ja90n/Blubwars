package com.blub.blubwars.instance;

import com.blub.blubwars.GameState;
import com.blub.blubwars.team.Team;
import org.bukkit.Bukkit;
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
            Bukkit.getPlayer(uuid).closeInventory();
            for (Team team : Team.values()){
                if (arena.getTeam(Bukkit.getPlayer(uuid)).equals(team)){
                    if (team.equals(Team.RED)) {
                        Bukkit.getPlayer(uuid).teleport(arena.getRedspawn());
                    } else if (team.equals(Team.BLUE)) {
                        Bukkit.getPlayer(uuid).teleport(arena.getBluespawn());
                    } else if (team.equals(Team.GREEN)) {
                        Bukkit.getPlayer(uuid).teleport(arena.getGreenspawn());
                    } else if (team.equals(Team.PINK)) {
                        Bukkit.getPlayer(uuid).teleport(arena.getPinkspawn());
                    }
                }
            }
        }
    }

    public void addPoint(Player player){
        int playerPoints = points.get(player.getUniqueId()) + 1;
        if (playerPoints == 10){
            arena.sendMessage(ChatColor.LIGHT_PURPLE + player.getName() + " has won! :)");
            arena.reset();
        } else {
            player.sendMessage(ChatColor.BLUE + "+1 point!");
            points.replace(player.getUniqueId(), playerPoints);
        }
    }
}
