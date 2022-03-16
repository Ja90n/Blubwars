package com.blub.blubwars.gui;

import com.blub.blubwars.Blubwars;
import com.blub.blubwars.instance.Arena;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ShopGui {

    private Inventory shopgui;

    public ShopGui(Player player, Blubwars blubwars, Arena arena){

        this.shopgui = Bukkit.createInventory(null,45, ChatColor.LIGHT_PURPLE + "Shop");

        ItemStack frame = new ItemStack(Material.PINK_STAINED_GLASS_PANE);
        ItemMeta framemeta = frame.getItemMeta();
        framemeta.setDisplayName(" ");
        frame.setItemMeta(framemeta);
        for (int i : new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 26, 27, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44}) {
            shopgui.setItem(i, frame);
        }

        // Wool
        ItemStack wool = new ItemStack(arena.getTeam(player).getMaterial());
        createItem(wool,null,ChatColor.GRAY + "Cost: 1 Cod",10,null,0);

        // Stone sword
        createItem(new ItemStack(Material.STONE_SWORD),ChatColor.GRAY + "Stone sword",
                ChatColor.GRAY + "Cost: 1 " + ChatColor.GOLD + "Salmon",11,null,0);

        // Fireball
        createItem(new ItemStack(Material.FIRE_CHARGE),ChatColor.GOLD + "Fireball",
                ChatColor.GRAY + "Cost: 5 " + ChatColor.GOLD + "Salmon",12,null,0);

        // Shears
        createItem(new ItemStack(Material.SHEARS),ChatColor.GRAY + "Shears",
                ChatColor.GRAY + "Cost: 5 " + "Cod", 13,Enchantment.DIG_SPEED,5);

        // Parachute
        createItem(new ItemStack(Material.FEATHER),ChatColor.GRAY + "Parachute",
                ChatColor.GRAY + "Cost: 10 " + "Cod", 14, null,0);

        player.openInventory(shopgui);
    }

    private void createItem(ItemStack itemStack, String displayName, String lore, int index,
                            Enchantment enchantment,int level){
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (displayName != null){
            itemMeta.setDisplayName(displayName);
        }
        if (lore != null){
            List<String> loreList = new ArrayList<>();
            loreList.add(lore);
            itemMeta.setLore(loreList);
        }
        if (enchantment != null){
            itemMeta.addEnchant(enchantment,level,true);
        }
        itemStack.setItemMeta(itemMeta);
        shopgui.setItem(index, itemStack);
    }
}