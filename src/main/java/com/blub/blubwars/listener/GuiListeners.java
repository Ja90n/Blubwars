package com.blub.blubwars.listener;

import com.blub.blubwars.Blubwars;
import com.blub.blubwars.enums.Team;
import com.blub.blubwars.utils.GivePlayerItem;
import com.blub.blubwars.instance.Arena;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class GuiListeners implements Listener {

    private Blubwars blubwars;
    int amount;
    int tries;
    private ArrayList<Integer> slot;

    public GuiListeners(Blubwars blubwars) {
        this.blubwars = blubwars;
        this.amount = 0;
        this.tries = 0;
        this.slot = new ArrayList<>();
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e){
        if (e.getInventory() != null && e.getCurrentItem() != null){
            Player player = (Player) e.getWhoClicked();
            if (e.getView().getTitle().contains("Team Selection")){
                e.setCancelled(true);
                Team team = Team.valueOf(e.getCurrentItem().getItemMeta().getLocalizedName());
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
            } else if (e.getView().getTitle().contains("Shop")){
                e.setCancelled(true);
                switch (e.getSlot()){
                    case 10:
                        ItemStack wool = new ItemStack(blubwars.getArenaManager().
                                getArena(player).getTeam(player).getMaterial());
                        new GivePlayerItem(player,new ItemStack(Material.COD),wool,1,4);
                        break;
                    case 11:
                        new GivePlayerItem(player,new ItemStack(Material.SALMON),
                                new ItemStack(Material.STONE_SWORD),1,1);
                        break;
                    case 12:
                        ItemStack fireball = new ItemStack(Material.FIRE_CHARGE);
                        ItemMeta fireballMeta = fireball.getItemMeta();
                        fireballMeta.setDisplayName(ChatColor.GOLD + "Fireball");
                        fireball.setItemMeta(fireballMeta);
                        new GivePlayerItem(player,new ItemStack(Material.SALMON),fireball, 5,1);
                        break;
                    case 13:
                        ItemStack shears = new ItemStack(Material.SHEARS);
                        ItemMeta shearsMeta = shears.getItemMeta();
                        shearsMeta.setDisplayName(ChatColor.GRAY + "Shears");
                        shears.addEnchantment(Enchantment.DIG_SPEED, 5);
                        shears.setItemMeta(shearsMeta);
                        new GivePlayerItem(player,new ItemStack(Material.COD),shears,5,1);
                        break;
                }
            }
        }
    }
}