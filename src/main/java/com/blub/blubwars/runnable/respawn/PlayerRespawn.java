package com.blub.blubwars.runnable.respawn;

import com.blub.blubwars.Blubwars;
import com.blub.blubwars.instance.Arena;
import com.blub.blubwars.utils.SetPlayerStartInventory;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerRespawn extends BukkitRunnable {

    private Player player;
    private Arena arena;
    private Blubwars blubwars;
    private int timeRun;

    public PlayerRespawn(Player player, Arena arena, Blubwars blubwars){
        this.player = player;
        this.arena = arena;
        this.blubwars = blubwars;
        this.timeRun = 0;
    }

    public void start(){
        runTaskTimer(blubwars,2,20);
    }

    @Override
    public void run() {
        if (timeRun == 0){
            player.spigot().respawn();
            player.setInvisible(true);
            player.setInvulnerable(true);
        }
        int countDown = 5 - timeRun;
        player.sendTitle(ChatColor.GRAY + "You are respawning in ", ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD + countDown + " seconds");
        if (timeRun == 5){
            player.setInvisible(false);
            player.setInvulnerable(false);
            player.sendTitle("","");
            try {
                new SetPlayerStartInventory(player,blubwars);
                player.teleport(arena.getTeamSpawn(arena.getTeam(player)));
            } catch (NullPointerException e){cancel();}
            cancel();
        }
        timeRun++;
    }
}
