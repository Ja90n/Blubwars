package com.blub.blubwars.runnable;

import com.blub.blubwars.Blubwars;
import com.blub.blubwars.enums.Team;
import com.blub.blubwars.instance.Arena;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.GameMode;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Cat;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;

public class CatRespawn extends BukkitRunnable {

    private Blubwars blubwars;
    private Arena arena;
    private Team team;
    private int catLivesInt;
    private HashMap<Team, UUID> hollograms;
    private HashMap<UUID,Integer> catLives;
    private int timeRun;

    public CatRespawn(Blubwars blubwars, Arena arena, Team team, int catLivesInt){
        this.blubwars = blubwars;
        this.arena = arena;
        this.hollograms = new HashMap<>();
        this.team = team;
        this.catLivesInt = catLivesInt;
        this.catLives = arena.getGame().getCatLives();
        this.timeRun = 0;
    }

    public void start(){
        runTaskTimer(blubwars,0,20);
    }

    @Override
    public void run() {
        if (catLivesInt-1 > 0){
            if (!(timeRun == 0)){
                Bukkit.getEntity(hollograms.get(team)).remove();
                hollograms.remove(team);
            } else {
                arena.sendMessage(team.getDisplay() + ChatColor.AQUA + " cat has " + (catLivesInt-1) + " lives left!");
            }
            if (!(timeRun == 5)){
                ArmorStand armorStand = (ArmorStand) arena.getWorld().spawnEntity(arena.getTeamSpawn(team), EntityType.ARMOR_STAND);
                armorStand.setCustomNameVisible(true);
                armorStand.setInvisible(true);
                armorStand.setInvulnerable(true);
                armorStand.setBasePlate(false);
                armorStand.setCustomName(team.getDisplay() + ChatColor.AQUA + " cat respawning in " + (5 - timeRun));
                hollograms.put(team,armorStand.getUniqueId());
            } else {
                Cat cat = (Cat) arena.getWorld().spawnEntity(arena.getTeamSpawn(team),EntityType.CAT);
                arena.getGame().getCatLives().put(cat.getUniqueId(),catLivesInt-1);
                cat.setCustomName(team.getDisplay() + " cat, lives: " + arena.getGame().getCatLives().get(cat.getUniqueId()));
                cat.setSitting(true);
                cat.setSilent(true);
                cat.setTamed(true);
                switch (team){
                    case RED:
                        cat.setCollarColor(DyeColor.RED);
                        break;
                    case BLUE:
                        cat.setCollarColor(DyeColor.BLUE);
                        break;
                    case GREEN:
                        cat.setCollarColor(DyeColor.GREEN);
                        break;
                    case PINK:
                        cat.setCollarColor(DyeColor.PINK);
                        break;
                }
                if (catLives.get(cat.getUniqueId()) > 4){
                    cat.setGlowing(true);
                }
                cancel();
            }
        } else {
            catLives.remove(arena.getCatUUID(team));
            if (catLives.size() <= 0){
                for (UUID target : catLives.keySet()){
                    arena.sendMessage(ChatColor.AQUA + "Team " + arena.getTeam(target).getDisplay() +
                            ChatColor.AQUA + " has won!");
                    arena.sendTitle(ChatColor.AQUA + "Team " + arena.getTeam(target).getDisplay() +
                            ChatColor.AQUA + " has won!", ChatColor.GRAY + "Thank you for playing!");
                }
                arena.reset();
            } else {
                arena.sendMessage(team.getDisplay() + ChatColor.AQUA + " teams cat has been eliminated!");
                for (UUID uuid : arena.getPlayers()){
                    Player player = Bukkit.getPlayer(uuid);
                    if (arena.getTeam(player).equals(team)){
                        player.setGameMode(GameMode.SPECTATOR);
                    }
                }
            }
            cancel();
        }
        timeRun++;
    }
}