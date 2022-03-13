package com.blub.blubwars.listener;

import com.blub.blubwars.Blubwars;
import com.blub.blubwars.enums.GameState;
import com.blub.blubwars.gui.ShopGui;
import com.blub.blubwars.instance.Arena;
import com.blub.blubwars.manager.ConfigManager;
import com.blub.blubwars.runnable.CatRespawn;
import com.blub.blubwars.runnable.PlayerRespawn;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Cat;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.event.weather.WeatherEvent;

import java.util.HashMap;
import java.util.jar.JarEntry;

public class GameListener implements Listener {

    private Blubwars blubwars;

    public GameListener(Blubwars blubwars) {
        this.blubwars = blubwars;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e){
        if (blubwars.getArenaManager().getArena(e.getPlayer()) != null) {
            if (blubwars.getArenaManager().getArena(e.getPlayer()).getState().equals(GameState.LIVE)){
                Player player = e.getPlayer();
                Arena arena = blubwars.getArenaManager().getArena(player);
                if (e.getPlayer().getLocation().getY() < 0){
                    e.getPlayer().setHealth(0);
                }
            }
        }
    }

    @EventHandler
    public void onWeahterChange(WeatherChangeEvent e){
        e.setCancelled(true);
        e.getWorld().setClearWeatherDuration(999999999);
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent e){
        if (e.getEntity() instanceof Cat){
            Cat cat = (Cat) e.getEntity();
            for (Arena arena : blubwars.getArenaManager().getArenas()){
                if (arena.getGame().getCatLives().containsKey(cat.getUniqueId())){
                    new CatRespawn(blubwars,arena,arena.getTeam(cat.getUniqueId()),arena.getGame().getCatLives().get(cat.getUniqueId())).start();
                    arena.getGame().getCatLives().remove(cat.getUniqueId());
                }
            }
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        if (blubwars.getArenaManager().getArena(e.getEntity().getPlayer()) != null) {
            if (blubwars.getArenaManager().getArena(e.getEntity().getPlayer()).getState().equals(GameState.LIVE)){
                Player player = e.getEntity();
                Arena arena = blubwars.getArenaManager().getArena(player);
                new PlayerRespawn(player,blubwars.getArenaManager().
                        getArena(e.getEntity().getPlayer()), blubwars).start();
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

    @EventHandler
    public void onHunger(FoodLevelChangeEvent e){
        e.setFoodLevel(20);
        e.setCancelled(true);
    }
}
