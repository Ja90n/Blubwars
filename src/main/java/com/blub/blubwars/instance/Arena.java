package com.blub.blubwars.instance;

import com.blub.blubwars.Blubwars;
import com.blub.blubwars.GameState;
import com.blub.blubwars.manager.ConfigManager;
import com.blub.blubwars.team.Team;
import com.google.common.collect.TreeMultimap;
import org.bukkit.*;
import org.bukkit.entity.Cat;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Arena {

    private Blubwars blubwars;

    private int id;
    private Location spawn;

    private GameState state;
    private List<UUID> players;
    private HashMap<UUID, Team> teams;
    private Countdown countdown;
    private Game game;
    private Location redspawn,bluespawn,greenspawn,pinkspawn;

    public Arena(Blubwars blubwars, int id, Location spawn){
        this.blubwars = blubwars;

        this.id = id;
        this.spawn = spawn;

        this.state = GameState.RECRUITING;
        this.players = new ArrayList<>();
        this.teams = new HashMap<>();

        this.countdown = new Countdown(blubwars, this);
        this.game = new Game(this);

        this.redspawn = new Location(
                getWorld(),
                blubwars.getConfig().getDouble("arenas." + id + ".red.x"),
                blubwars.getConfig().getDouble("arenas." + id + ".red.y"),
                blubwars.getConfig().getDouble("arenas." + id + ".red.z"),
                (float) blubwars.getConfig().getDouble("arenas." + id + ".red.yaw"),
                (float) blubwars.getConfig().getDouble("arenas." + id + ".red.pitch"));
        this.bluespawn = new Location(
                getWorld(),
                blubwars.getConfig().getDouble("arenas." + id + ".blue.x"),
                blubwars.getConfig().getDouble("arenas." + id + ".blue.y"),
                blubwars.getConfig().getDouble("arenas." + id + ".blue.z"),
                (float) blubwars.getConfig().getDouble("arenas." + id + ".blue.yaw"),
                (float) blubwars.getConfig().getDouble("arenas." + id + ".blue.pitch"));
        this.greenspawn = new Location(
                getWorld(),
                blubwars.getConfig().getDouble("arenas." + id + ".green.x"),
                blubwars.getConfig().getDouble("arenas." + id + ".green.y"),
                blubwars.getConfig().getDouble("arenas." + id + ".green.z"),
                (float) blubwars.getConfig().getDouble("arenas." + id + ".green.yaw"),
                (float) blubwars.getConfig().getDouble("arenas." + id + ".green.pitch"));
        this.pinkspawn = new Location(
                getWorld(),
                blubwars.getConfig().getDouble("arenas." + id + ".pink.x"),
                blubwars.getConfig().getDouble("arenas." + id + ".pink.y"),
                blubwars.getConfig().getDouble("arenas." + id + ".pink.z"),
                (float) blubwars.getConfig().getDouble("arenas." + id + ".pink.yaw"),
                (float) blubwars.getConfig().getDouble("arenas." + id + ".pink.pitch"));
    }

    // Games

    public void start(){
        game.start();
    }

    public void reset(){
        if (state.equals(GameState.LIVE)){
            Location lobbyspawn = ConfigManager.getLobbySpawn();
            for (UUID uuid : players){
                Bukkit.getPlayer(uuid).teleport(lobbyspawn);
            }
            players.clear();
            teams.clear();
            game.clearCatLives();
        }
        sendTitle("", "");
        state = GameState.RECRUITING;
        try {
            countdown.cancel();
            countdown = new Countdown(blubwars, this);
        } catch (IllegalStateException e){}
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

        TreeMultimap<Integer, Team> count = TreeMultimap.create();
        for (Team team : Team.values()){
            count.put(getTeamCount(team), team);
        }

        Team lowest = (Team) count.values().toArray()[0];
        setTeam(player, lowest);

        player.teleport(spawn);

        player.sendMessage(ChatColor.BLUE + "You have been placed in " + lowest.getDisplay());

        if (state.equals(GameState.RECRUITING) && players.size() >= ConfigManager.getRequiredPlayers()){
            countdown.start();
        }
    }

    public void removePlayer(Player player){
        players.remove(player.getUniqueId());
        player.teleport(ConfigManager.getLobbySpawn());
        player.sendTitle("", "");

        removeTeam(player);

        if (state == GameState.COUNTDOWN && players.size() < ConfigManager.getRequiredPlayers()){
            sendMessage(ChatColor.RED + "There are not enough players, countdown stopped!");
            reset();
            return;
        }

        if (state == GameState.LIVE && players.size() < ConfigManager.getRequiredPlayers()){
            sendMessage(ChatColor.RED + "The game has ended as to many players have left.");
            reset();
        }
    }

    // Manager

    public int getId() { return id; }
    public World getWorld(){ return spawn.getWorld();}

    public GameState getState() { return state; }
    public List<UUID> getPlayers() { return players; }

    public Game getGame () { return game; }

    public void setState(GameState state) { this.state = state; }

    public void setTeam(Player player, Team team){
        removeTeam(player);
        teams.put(player.getUniqueId(), team);
    }

    public void removeTeam(Player player){
        if (teams.containsKey(player.getUniqueId())){
            teams.remove(player.getUniqueId());
        }
    }

    public int getTeamCount(Team team) {
        int amount = 0;
        for (Team t : teams.values()) {
            if (t == team){
                amount++;
            }
        }
        return amount;
    }

    public Team getTeam (Player player){ return teams.get(player.getUniqueId()); }

    public Team getTeam (UUID catUUID){
        Cat cat = (Cat) Bukkit.getEntity(catUUID);
        if (cat.getCollarColor().equals(DyeColor.RED)){
            return Team.RED;
        } else if (cat.getCollarColor().equals(DyeColor.BLUE)){
            return Team.BLUE;
        } else if (cat.getCollarColor().equals(DyeColor.GREEN)){
            return Team.GREEN;
        } else if (cat.getCollarColor().equals(DyeColor.PINK)){
            return Team.PINK;
        } else {
            return null;
        }
    }

    public UUID getCatUUID(Team team){
        UUID uuid = null;
        for (UUID catUUID : game.getCatLives().keySet()){
            Cat cat = (Cat) Bukkit.getEntity(catUUID);
            if (team.equals(Team.RED)){
                if (cat.getCollarColor().equals(DyeColor.RED)){
                    uuid = catUUID;
                }
            } else if (team.equals(Team.BLUE)){
                if (cat.getCollarColor().equals(DyeColor.BLUE)){
                    uuid = catUUID;
                }
            } else if (team.equals(Team.GREEN)){
                if (cat.getCollarColor().equals(DyeColor.GREEN)){
                    uuid = catUUID;
                }
            } else if (team.equals(Team.PINK)){
                if (cat.getCollarColor().equals(DyeColor.PINK)){
                    uuid = catUUID;
                }
            }
        }
        return uuid;
    }

    public HashMap<UUID, Team> getTeams (){ return teams; }

    public Location getTeamSpawn(Team team){
        if (team.equals(Team.RED)){
            return redspawn;
        } else if (team.equals(Team.BLUE)){
            return bluespawn;
        } else if (team.equals(Team.GREEN)){
            return greenspawn;
        } else if (team.equals(Team.PINK)){
            return pinkspawn;
        } else {
            return null;
        }
    }

}
