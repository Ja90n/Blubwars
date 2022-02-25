package com.blub.blubwars.instance;

import com.blub.blubwars.GameState;
import com.blub.blubwars.manager.configManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Arena {

    private int id;
    private Location spawn;

    private GameState state;
    private List<UUID> players;

    public Arena(int id, Location spawn){
        this.id = id;
        this.spawn = spawn;

        this.state = GameState.RECRUITING;
        this.players = new ArrayList<>();
    }

    public void addPlayer(Player player){
        players.add(player.getUniqueId());
        player.teleport(spawn);
    }

    public void removePlayer(Player player){
        players.remove(player.getUniqueId());
        player.teleport(configManager.getLobbySpawn());
    }


    public int getId() { return id; }

    public GameState getState() { return state; }
    public List<UUID> getPlayers() { return players; }
}
