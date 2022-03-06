package com.blub.blubwars.instance;

import com.blub.blubwars.Blubwars;
import com.blub.blubwars.GameState;
import com.blub.blubwars.manager.ConfigManager;
import com.blub.blubwars.team.Team;
import com.google.common.collect.TreeMultimap;
import org.bukkit.*;
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
    private boolean canJoin;
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
        this.canJoin = true;

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
            setCanJoin(false);
            Location lobbyspawn = ConfigManager.getLobbySpawn();
            for (UUID uuid : players){
                Bukkit.getPlayer(uuid).teleport(lobbyspawn);
            }
            players.clear();
            teams.clear();

            String worldname = spawn.getWorld().getName();
            Bukkit.unloadWorld(spawn.getWorld(), false);
            World world = Bukkit.createWorld(new WorldCreator(worldname));
            world.setAutoSave(false);
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

    public boolean getCanJoin() { return canJoin; }
    public void setCanJoin(Boolean canjoin) { this.canJoin = canjoin; }

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
    public HashMap<UUID, Team> getTeams (){ return teams; }

    public Location getRedspawn() { return redspawn;}
    public Location getBluespawn() { return bluespawn;}
    public Location getGreenspawn() { return greenspawn;}
    public Location getPinkspawn() { return pinkspawn;}

}
