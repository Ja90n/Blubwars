package com.blub.blubwars.listener;

import com.blub.blubwars.Blubwars;
import com.blub.blubwars.gui.ShopGui;
import com.blub.blubwars.instance.Arena;
import com.blub.blubwars.Team;
import org.bukkit.ChatColor;
import org.bukkit.entity.Cat;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

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
        } else if (e.getInventory() != null && e.getCurrentItem() != null && e.getView().getTitle().contains("Shop")){
            if (e.getSlot() == 10){
                Player player = (Player) e.getWhoClicked();
                ItemStack wool = new ItemStack(blubwars.getArenaManager().getArena(player).getTeam(player).getMaterial());
                ItemMeta woolMeta = wool.getItemMeta();
                woolMeta.setDisplayName(blubwars.getArenaManager().getArena(player).getTeam(player).getDisplay() + " wool");
                wool.setItemMeta(woolMeta);
                e.getWhoClicked().getInventory().addItem(wool);
            }
            e.setCancelled(true);
        }
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
