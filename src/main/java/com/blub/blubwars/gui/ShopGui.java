package com.blub.blubwars.gui;

import com.blub.blubwars.Blubwars;
import com.blub.blubwars.Team;
import com.blub.blubwars.instance.Arena;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ShopGui {

    public ShopGui(Player player, Blubwars blubwars, Arena arena){
        Inventory shopgui = Bukkit.createInventory(null,45, ChatColor.LIGHT_PURPLE + "Shop");

        ItemStack frame = new ItemStack(Material.PINK_STAINED_GLASS_PANE);
        ItemMeta framemeta = frame.getItemMeta();
        framemeta.setDisplayName(" ");
        frame.setItemMeta(framemeta);
        for (int i : new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 26, 27, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44}) {
            shopgui.setItem(i, frame);
        }

        ItemStack wool = new ItemStack(arena.getTeam(player).getMaterial());
        ItemMeta woolMeta = wool.getItemMeta();
        woolMeta.setDisplayName(arena.getTeam(player).getDisplay() + " wool");
        wool.setItemMeta(woolMeta);
        shopgui.setItem(10,wool);

        player.openInventory(shopgui);
    }
}
