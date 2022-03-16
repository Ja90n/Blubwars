package com.blub.blubwars.utils;

import com.blub.blubwars.Blubwars;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class SuckerRunnable extends BukkitRunnable {

    private Location snowball;
    private org.bukkit.util.Vector vector;
    private List<Entity> entities;
    private int timeRun;
    private Blubwars blubwars;

    public SuckerRunnable(Location snowball, List<Entity> entities, Blubwars blubwars){
        this.snowball = snowball;
        this.vector = snowball.toVector();
        this.entities = entities;
        this.timeRun = 0;
        this.blubwars = blubwars;
    }

    public void start(){
        runTaskTimer(blubwars,20,5);
    }

    @Override
    public void run() {
        for (Entity entity : entities){
            vector = snowball.toVector().subtract(entity.getLocation().toVector()).multiply(0.3);
            entity.setVelocity(vector);
        }
        if (timeRun == 20){
            cancel();
        }
        timeRun++;
    }
}
