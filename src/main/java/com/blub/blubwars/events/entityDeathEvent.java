package com.blub.blubwars.events;

import com.blub.blubwars.Blubwars;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.entity.Cat;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class entityDeathEvent implements Listener {

    int lives = 9;

    private Blubwars blubwars;
    public entityDeathEvent(Blubwars blubwars) {
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent e){
        if (e.getEntity() instanceof Player){
            Player p = (Player) e.getEntity();

        } else if (e.getEntity() instanceof Cat){
            Cat cat = (Cat) e.getEntity();
            if (cat.getCollarColor().equals(DyeColor.PINK)){
                try {
                    Player p = Bukkit.getPlayer(cat.getOwner().getUniqueId());
                    lives--;
                    if (lives > 0){
                        Cat spawncat = (Cat) p.getWorld().spawnEntity(p.getLocation(), EntityType.CAT);
                        spawncat.setCatType(Cat.Type.BLACK);
                        spawncat.setCollarColor(DyeColor.PINK);
                        spawncat.setCustomName(ChatColor.LIGHT_PURPLE + "Pink cat: " + ChatColor.BLUE + lives + " lives");
                        spawncat.setOwner(p);
                    } else {
                        p.sendMessage("oomf");
                    }
                } catch (NullPointerException exception){
                    Bukkit.getConsoleSender().sendMessage("oomf");
                }
            }
        }
    }
}
