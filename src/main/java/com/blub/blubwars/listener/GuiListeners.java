package com.blub.blubwars.listener;

import com.blub.blubwars.Blubwars;
import com.blub.blubwars.enums.Team;
import com.blub.blubwars.instance.Arena;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GuiListeners implements Listener {

    private Blubwars blubwars;

    public GuiListeners(Blubwars blubwars) {
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
                if (player.getInventory().contains(Material.COD)){
                    player.getInventory().remove(Material.COD);
                    ItemStack wool = new ItemStack(blubwars.getArenaManager().getArena(player).getTeam(player).getMaterial());
                    ItemMeta woolMeta = wool.getItemMeta();
                    woolMeta.setDisplayName(blubwars.getArenaManager().getArena(player).getTeam(player).getDisplay() + " wool");
                    wool.setItemMeta(woolMeta);
                    for (int i = 0; i < 4; i++){
                        e.getWhoClicked().getInventory().addItem(wool);
                    }
                }
            }
            e.setCancelled(true);
        }
    }
}
