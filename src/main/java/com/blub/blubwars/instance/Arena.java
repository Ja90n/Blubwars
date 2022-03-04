package com.blub.blubwars.instance;

import com.blub.blubwars.Blubwars;
import com.blub.blubwars.GameState;
import com.blub.blubwars.manager.configManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Arena {

    private Blubwars blubwars;

    private int id;
    private Location spawn;

    private GameState state;
    private List<UUID> players;
    private Countdown countdown;
    private Game game;

    public Arena(Blubwars blubwars, int id, Location spawn){
        this.blubwars = blubwars;

        this.id = id;
        this.spawn = spawn;

        this.state = GameState.RECRUITING;
        this.players = new ArrayList<>();
        this.countdown = new Countdown(blubwars, this);
        this.game = new Game(this);
    }

    // Games

    public void start(){
        game.start();
    }

    public void reset(boolean kickPlayers){
        if (kickPlayers){
            Location lobbyspawn = configManager.getLobbySpawn();
            for (UUID uuid : players){
                Bukkit.getPlayer(uuid).teleport(lobbyspawn);
            }
            players.clear();
        }
        sendTitle("", "");
        state = GameState.RECRUITING;
        countdown.cancel();
        countdown = new Countdown(blubwars, this);
        game = new Game(this);
    }

    // Tools

    public void sendMessage(String message){
        for (UUID uuid : players){
            Bukkit.getPlayer(uuid).sendMessage(message);
        }
    }

    public void sendTitle(String title, String subtitle){
        for (UUID uuid : players){
            Bukkit.getPlayer(uuid).sendTitle(title,subtitle);
        }
    }

    // Players

    public void addPlayer(Player player){
        players.add(player.getUniqueId());
        player.teleport(spawn);

        if (state.equals(GameState.RECRUITING) && players.size() >= configManager.getRequiredPlayers()){
            countdown.start();
        }
    }

    public void removePlayer(Player player){
        players.remove(player.getUniqueId());
        player.teleport(configManager.getLobbySpawn());
        player.sendTitle("", "");

        if (state == GameState.COUNTDOWN && players.size() < configManager.getRequiredPlayers()){
            sendMessage(ChatColor.RED + "There are not enough players, countdown stopped!");
            reset(false);
            return;
        }

        if (state == GameState.LIVE && players.size() < configManager.getRequiredPlayers()){
            sendMessage(ChatColor.RED + "The game has ended as to many players have left.");
            reset(true);
        }
    }

    // Manager

    public int getId() { return id; }

    public GameState getState() { return state; }
    public List<UUID> getPlayers() { return players; }

    public Game getGame () { return game; }

    public void setState(GameState state) { this.state = state; }

}
