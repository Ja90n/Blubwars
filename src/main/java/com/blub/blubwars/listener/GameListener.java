package com.blub.blubwars.listener;

import com.blub.blubwars.Blubwars;
import com.blub.blubwars.gui.ShopGui;
import com.blub.blubwars.instance.Arena;
import org.bukkit.entity.Cat;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

import java.util.HashMap;

public class GameListener implements Listener {

    private Blubwars blubwars;
    private HashMap<Cat,Integer> catLives;

    public GameListener(Blubwars blubwars) {
        this.blubwars = blubwars;
    }


    @EventHandler
    public void onDeath(EntityDeathEvent e){
        if (e.getEntity() instanceof Cat){
            Cat cat = (Cat) e.getEntity();
            for (Arena arena : blubwars.getArenaManager().getArenas()){
                if (arena.getGame().getCatLives().containsKey(cat.getUniqueId())){
                    arena.getGame().respawnCat(cat.getUniqueId());
                }
            }
        }
    }

    @EventHandler
    public void onEntityClick(PlayerInteractAtEntityEvent e){
        if (e.getRightClicked() instanceof Villager){
            for (Arena arena : blubwars.getArenaManager().getArenas()){
                if (arena.getGame().getVillagerShop().containsValue(e.getRightClicked().getUniqueId())){
                    new ShopGui(e.getPlayer(), blubwars, arena);
                }
            }
        }
    }
}
