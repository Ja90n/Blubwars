package com.blub.blubwars.listener;

import com.blub.blubwars.Blubwars;
import com.blub.blubwars.GameState;
import com.blub.blubwars.instance.Arena;
import com.blub.blubwars.instance.Game;
import com.blub.blubwars.team.Team;
import org.bukkit.ChatColor;
import org.bukkit.entity.Cat;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.HashMap;

public class GameListener implements Listener {

    private Blubwars blubwars;
    private HashMap<Cat,Integer> catLives;

    public GameListener(Blubwars blubwars) {
        this.blubwars = blubwars;
    }


    @EventHandler
    public void onInventoryClick(InventoryClickEvent e){
        if (e.getInventory() != null && e.getCurrentItem() != null && e.getView().getTitle().contains("Team Selection")){

            Team team = Team.valueOf(e.getCurrentItem().getItemMeta().getLocalizedName());
            Player player = (Player) e.getWhoClicked();

            Arena arena = blubwars.getArenaManager().getArena(player);
            if (arena != null){
                if (arena.getTeam(player) == team){
                    player.sendMessage(ChatColor.RED + "You are already on that team!");
                } else {
                    player.sendMessage(ChatColor.BLUE + "You are now in " + team.getDisplay());
                    arena.setTeam(player, team);
                }
            }
            player.closeInventory();
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e){

        Arena arena = blubwars.getArenaManager().getArena(e.getPlayer());
        if (arena != null && arena.getState().equals(GameState.LIVE)){
            arena.getGame().addPoint(e.getPlayer());
        }
    }

    @EventHandler
    public void onDeath(EntityDeathEvent e){
        if (e.getEntity() instanceof Cat){
            Cat cat = (Cat) e.getEntity();
            for (Arena arena : blubwars.getArenaManager().getArenas()){
                if (arena.getGame().getCatLives().containsKey(cat)){
                    new Game(arena).removeCatLive(cat);
                }
            }
        }
    }
}
