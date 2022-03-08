package com.blub.blubwars.instance;

import com.blub.blubwars.GameState;
import com.blub.blubwars.team.Team;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.entity.Cat;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class Game {

    private DyeColor dyeColor;
    private HashMap<Cat, Integer> catLives;
    private Arena arena;
    private HashMap<UUID, Integer> points;

    public Game(Arena arena) {
        this.arena = arena;
        points = new HashMap<>();
    }

    public void start() {
        arena.setState(GameState.LIVE);
        arena.sendMessage(ChatColor.BLUE + "Game has started!");
        for (UUID uuid : arena.getPlayers()) {
            points.put(uuid, 0);
            Bukkit.getPlayer(uuid).closeInventory();
            for (Team team : Team.values()) {
                if (arena.getTeam(Bukkit.getPlayer(uuid)).equals(team)) {
                    if (team.equals(Team.RED)) {
                        dyeColor = DyeColor.RED;
                        Bukkit.getPlayer(uuid).teleport(arena.getRedspawn());
                    } else if (team.equals(Team.BLUE)) {
                        dyeColor = DyeColor.BLUE;
                        Bukkit.getPlayer(uuid).teleport(arena.getBluespawn());
                    } else if (team.equals(Team.GREEN)) {
                        dyeColor = DyeColor.GREEN;
                        Bukkit.getPlayer(uuid).teleport(arena.getGreenspawn());
                    } else if (team.equals(Team.PINK)) {
                        dyeColor = DyeColor.PINK;
                        Bukkit.getPlayer(uuid).teleport(arena.getPinkspawn());
                    }
                }
                if (arena.getTeamCount(team) > 0) {
                    Cat cat = (Cat) arena.getWorld().spawnEntity
                            (new Location(arena.getWorld(), 0, 0, 0, 0, 0), EntityType.CAT);
                    cat.setCollarColor(dyeColor);
                    catLives.put(cat, 9);
                    cat.setCustomName(team.getDisplay() + " cat, Lives: " + catLives.get(cat));
                }
            }
        }
    }

    public void addPoint(Player player) {
        int playerPoints = points.get(player.getUniqueId()) + 1;
        if (playerPoints == 10) {
            arena.sendMessage(ChatColor.LIGHT_PURPLE + player.getName() + " has won! :)");
            arena.reset();
        } else {
            player.sendMessage(ChatColor.BLUE + "+1 point!");
            points.replace(player.getUniqueId(), playerPoints);
        }
    }

    public void removeCatLive(Cat cat) {
        int catLive = catLives.get(cat) - 1;
        if (catLive == 0) {
            catLives.remove(cat);
            if (catLives.size() == 1) {
                for (Cat catWin : catLives.keySet()) {
                    switch (catWin.getCollarColor()) {
                        case RED:
                            arena.sendMessage(ChatColor.RED + "Team Red " + ChatColor.AQUA + "has won!");
                            break;
                        case BLUE:
                            arena.sendMessage(ChatColor.BLUE + "Team Blue " + ChatColor.AQUA + "has won!");
                            break;
                        case YELLOW:
                            arena.sendMessage(ChatColor.YELLOW + "Team Yellow " + ChatColor.AQUA + "has won!");
                            break;
                        case PINK:
                            arena.sendMessage(ChatColor.LIGHT_PURPLE + "Team Pink " + ChatColor.AQUA + "has won!");
                            break;
                        default:
                            arena.sendMessage(ChatColor.DARK_RED + "What the hell happened here?");
                            break;
                    }
                }
                arena.reset();
            }
        } else {

        }
    }

    public void spawnCat(Cat cat) {
        switch (cat.getCollarColor()) {
            case RED:
                Cat newCat = (Cat) arena.getWorld().spawnEntity(arena.getRedspawn(), EntityType.CAT);
                newCat.setCollarColor(cat.getCollarColor());
                newCat.setCustomName(cat.getCustomName());
                break;
            case BLUE:

                break;
            case YELLOW:

                break;
            case PINK:

                break;
            default:
                arena.sendMessage(ChatColor.DARK_RED + "What the hell happened here?");
                break;
        }
    }

    public void spawnCat(Team team) {
        if (team.equals(Team.RED)) {
            dyeColor = DyeColor.RED;

        } else if (team.equals(Team.BLUE)) {
            dyeColor = DyeColor.BLUE;

        } else if (team.equals(Team.GREEN)) {
            dyeColor = DyeColor.GREEN;
        } else if (team.equals(Team.PINK)) {
            dyeColor = DyeColor.PINK;
        }
    }



    public HashMap<Cat,Integer> getCatLives() { return catLives; }
    public void clearCatLives() {  catLives.clear(); }
}
