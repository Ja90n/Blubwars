package com.blub.blubwars.instance;

import com.blub.blubwars.Blubwars;
import com.blub.blubwars.enums.GameState;
import com.blub.blubwars.manager.ConfigManager;
import com.blub.blubwars.enums.Team;
import com.blub.blubwars.runnable.game.Dropper;
import com.blub.blubwars.utils.ArenaSchematics;
import com.blub.blubwars.utils.NoPlacingCuboids;
import com.blub.blubwars.utils.SetPlayerStartInventory;
import org.bukkit.*;
import org.bukkit.entity.Cat;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;

import java.util.HashMap;
import java.util.UUID;

public class Game {

    private Blubwars blubwars;
    private Dropper dropper;
    private HashMap<UUID, Integer> catLives;
    private HashMap<Team,UUID> villagerShops;
    private NoPlacingCuboids noPlacingCuboids;
    private Arena arena;

    public Game(Arena arena, Blubwars blubwars) {
        this.blubwars = blubwars;
        this.arena = arena;
        this.noPlacingCuboids = new NoPlacingCuboids(arena);
        this.dropper = new Dropper(arena,blubwars);
        catLives = new HashMap<>();
        villagerShops = new HashMap<>();
    }

    public void start() {
        new ArenaSchematics(arena,blubwars).saveSchematic();
        arena.setState(GameState.LIVE);
        noPlacingCuboids.CreateCuboids();
        arena.getWorld().setTime(0);
        arena.getWorld().setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
        arena.getWorld().setGameRule(GameRule.DO_MOB_SPAWNING, false);

        arena.sendTitle(ChatColor.GRAY + "Game has started!", ChatColor.LIGHT_PURPLE + "Good luck with killing cats!");

        for (UUID uuid : arena.getPlayers()){
            Player player = Bukkit.getPlayer(uuid);
            player.teleport(arena.getTeamSpawn(arena.getTeam(player)));
            player.setGameMode(GameMode.SURVIVAL);
            new SetPlayerStartInventory(player,blubwars);
        }

        for (Team team : Team.values()){
            if (team != null){
                arena.sendMessage("team go " + team.getDisplay());
                if (arena.getTeamCount(team) > 0){
                    arena.sendMessage("team cat " + team.getDisplay());
                    spawnCat(team);
                    arena.sendMessage("team villager " + team.getDisplay());
                    spawnVillagerShop(team);
                }
            }
        }
        dropper.start();
        arena.sendMessage(ChatColor.AQUA + "Game has started!");

    }

    public void spawnVillagerShop(Team team){
        Villager villager = (Villager) arena.getWorld().spawnEntity(arena.getVillagerSpawn(team), EntityType.VILLAGER);
        villager.setProfession(Villager.Profession.NITWIT);
        villager.setVillagerType(Villager.Type.SNOW);
        villager.setPersistent(true);
        villager.setSilent(true);
        villager.setAI(false);
        villager.setInvulnerable(true);
        villager.setCustomName(team.getDisplay() + " villager");
        villagerShops.put(team, villager.getUniqueId());
    }

    public void spawnCat(Team team){
        Cat cat = (Cat) arena.getWorld().spawnEntity(arena.getTeamSpawn(team), EntityType.CAT);
        catLives.put(cat.getUniqueId(), ConfigManager.getCatLives());
        DyeColor dyeColor = DyeColor.WHITE;
        switch (team){
            case RED:
                dyeColor = DyeColor.RED;
                break;
            case BLUE:
                dyeColor = DyeColor.BLUE;
                break;
            case GREEN:
                dyeColor = DyeColor.GREEN;
                break;
            case PINK:
                dyeColor = DyeColor.PINK;
                break;
        }
        cat.setSilent(true);
        cat.setTamed(true);
        cat.setSitting(true);
        cat.setCollarColor(dyeColor);
        cat.setCustomName(team.getDisplay() + " cat, lives: " + catLives.get(cat.getUniqueId()));
    }

    public HashMap<UUID,Integer> getCatLives() { return catLives; }
    public HashMap<Team,UUID> getVillagerShop() { return villagerShops; }
    public Dropper getDropper() { return dropper; }
    public NoPlacingCuboids getNoPlacingCuboids() { return noPlacingCuboids; }
}
