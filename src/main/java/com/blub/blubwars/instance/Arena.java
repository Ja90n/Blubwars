package com.blub.blubwars.instance;

import com.blub.blubwars.Blubwars;
import com.blub.blubwars.utils.ResetArena;
import com.blub.blubwars.enums.GameState;
import com.blub.blubwars.manager.ConfigManager;
import com.blub.blubwars.enums.Team;
import com.blub.blubwars.runnable.Countdown;
import com.google.common.collect.TreeMultimap;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Cat;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.*;
import java.util.*;

import static org.bukkit.Bukkit.addRecipe;
import static org.bukkit.Bukkit.getServer;

public class Arena {

    private Blubwars blubwars;

    private int id;
    private Location spawn;

    private GameState state;
    private List<UUID> players;
    private HashMap<UUID, Team> teams;
    private Countdown countdown;
    private Game game;

    public Arena(Blubwars blubwars, int id, Location spawn){
        this.blubwars = blubwars;

        this.id = id;
        this.spawn = spawn;

        this.state = GameState.RECRUITING;
        this.players = new ArrayList<>();
        this.teams = new HashMap<>();

        this.countdown = new Countdown(blubwars, this);
        this.game = new Game(this,blubwars);
    }

    public Location getTeamSpawn(Team team){
        return new Location(
                getWorld(),
                blubwars.getConfig().getDouble("arenas." + id + "." + team.getTeamName() + ".team-spawn.x"),
                blubwars.getConfig().getDouble("arenas." + id + "." + team.getTeamName() + ".team-spawn.y"),
                blubwars.getConfig().getDouble("arenas." + id + "." + team.getTeamName() + ".team-spawn.z"),
                (float) blubwars.getConfig().getDouble("arenas." + id + "." + team.getTeamName() + ".team-spawn.yaw"),
                (float) blubwars.getConfig().getDouble("arenas." + id + "." + team.getTeamName() + ".team-spawn.pitch")
        );
    }

    public Location getVillagerSpawn(Team team){
        return new Location(
                getWorld(),
                blubwars.getConfig().getDouble("arenas." + id + "." + team.getTeamName() + ".villager-spawn.x"),
                blubwars.getConfig().getDouble("arenas." + id + "." + team.getTeamName() + ".villager-spawn.y"),
                blubwars.getConfig().getDouble("arenas." + id + "." + team.getTeamName() + ".villager-spawn.z"),
                (float) blubwars.getConfig().getDouble("arenas." + id + "." + team.getTeamName() + ".villager-spawn.yaw"),
                (float) blubwars.getConfig().getDouble("arenas." + id + "." + team.getTeamName() + ".villager-spawn.pitch")
        );
    }

    public Location getTeamDropper(Team team){
        return new Location(
                getWorld(),
                blubwars.getConfig().getDouble("arenas." + id + "." + team.getTeamName() + ".dropper.x"),
                blubwars.getConfig().getDouble("arenas." + id + "." + team.getTeamName() + ".dropper.y"),
                blubwars.getConfig().getDouble("arenas." + id + "." + team.getTeamName() + ".dropper.z"),
                (float) blubwars.getConfig().getDouble("arenas." + id + "." + team.getTeamName() + ".dropper.yaw"),
                (float) blubwars.getConfig().getDouble("arenas." + id + "." + team.getTeamName() + ".dropper.pitch")
        );
    }

    public Location getMidDropper(int dropper){
        return new Location(
                getWorld(),
                blubwars.getConfig().getDouble("arenas." + id + ".droppers." + dropper + ".x"),
                blubwars.getConfig().getDouble("arenas." + id + ".droppers." + dropper + ".y"),
                blubwars.getConfig().getDouble("arenas." + id + ".droppers." + dropper + ".z")
        );
    }

    // Games

    public void start(){game.start();}

    public void reset(){
        if (state.equals(GameState.LIVE)){
            Location lobbyspawn = ConfigManager.getLobbySpawn();
            for (UUID uuid : players){
                Bukkit.getPlayer(uuid).teleport(lobbyspawn);
                Bukkit.getPlayer(uuid).setGameMode(GameMode.ADVENTURE);
                Bukkit.getPlayer(uuid).getInventory().clear();
                Bukkit.getPlayer(uuid).getEnderChest().clear();
            }
            game.getDropper().cancel();
            players.clear();
            teams.clear();
            for (UUID catUUID : game.getCatLives().keySet()){
                Bukkit.getEntity(catUUID).remove();
                game.getCatLives().remove(catUUID);
            }
            game.getCatLives().clear();
            for (UUID villagerUUID : game.getVillagerShop().values()){
                Bukkit.getEntity(villagerUUID).remove();
                game.getVillagerShop().remove(villagerUUID);
            }
            game.getVillagerShop().clear();
            new ResetArena(this);
        }
        sendTitle("", "");
        state = GameState.RECRUITING;
        try {
            countdown.cancel();
            countdown = new Countdown(blubwars, this);
        } catch (IllegalStateException e){}
        game = new Game(this,blubwars);
        new ResetArena(this);
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
        player.setGameMode(GameMode.ADVENTURE);

        TreeMultimap<Integer, Team> count = TreeMultimap.create();
        for (Team team : Team.values()){
            count.put(getTeamCount(team), team);
        }

        Team lowest = (Team) count.values().toArray()[0];
        setTeam(player, lowest);

        player.teleport(spawn);

        player.getInventory().clear();

        player.sendMessage(ChatColor.BLUE + "You have been placed in " + lowest.getDisplay());

        if (state.equals(GameState.RECRUITING) && players.size() >= ConfigManager.getRequiredPlayers()){
            countdown.start();
        }
    }

    public void removePlayer(Player player){
        players.remove(player.getUniqueId());
        player.setGameMode(GameMode.SURVIVAL);
        player.getInventory().clear();
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
}
