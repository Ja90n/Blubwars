package com.blub.blubwars.runnable;

import com.blub.blubwars.Blubwars;
import com.blub.blubwars.enums.Team;
import com.blub.blubwars.instance.Arena;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import java.util.HashMap;

public class Dropper extends BukkitRunnable {

    private Blubwars blubwars;
    private Arena arena;
    private HashMap<Team, Location> teamDroppers;
    int timeRun;

    public Dropper(Arena arena, Blubwars blubwars){
        this.blubwars = blubwars;
        this.arena = arena;
        this.timeRun = 0;
    }

    public void start(){
        for (Team team : arena.getTeams().values()){
            if (arena.getTeamCount(team) > 0){
                teamDroppers.put(team,arena.getTeamSpawn(team));
            }
        }
        arena.sendMessage(ChatColor.GRAY + "Dropper initiated");
        runTaskTimer(blubwars, 0,20);
    }

    @Override
    public void run() {
        ItemStack cod = new ItemStack(Material.COD);
        ItemMeta codMeta = cod.getItemMeta();
        codMeta.setDisplayName(ChatColor.GRAY + "Cod");
        cod.setItemMeta(codMeta);

        ItemStack salmon = new ItemStack(Material.SALMON);
        ItemMeta salmonMeta = salmon.getItemMeta();
        salmonMeta.setDisplayName(ChatColor.GOLD + "Salmon");
        salmon.setItemMeta(salmonMeta);

        if (timeRun % 2 == 0){
            for (Location location : teamDroppers.values()){
                arena.getWorld().dropItem(location,cod);
                arena.sendMessage(ChatColor.GRAY + "Drop cod");
            }
        }
        if (timeRun % 10 == 0){
            for (Location location : teamDroppers.values()){
                arena.getWorld().dropItem(location,salmon);
                arena.sendMessage(ChatColor.GRAY + "Drop salmon");
            }
        }
        timeRun++;
    }
}
