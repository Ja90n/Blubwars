package com.blub.blubwars.gui;

import com.blub.blubwars.Blubwars;
import com.blub.blubwars.instance.Arena;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

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

        // Wool
        ItemStack wool = new ItemStack(arena.getTeam(player).getMaterial());
        ItemMeta woolMeta = wool.getItemMeta();
        woolMeta.setDisplayName(arena.getTeam(player).getDisplay() + " wool");
        List loreWool = new ArrayList<>();
        loreWool.add(ChatColor.GRAY + "Cost: 1 Cod");
        woolMeta.setLore(loreWool);
        wool.setItemMeta(woolMeta);
        wool.setAmount(4);
        shopgui.setItem(10,wool);

        // Stone sword
        List loreStoneSword = new ArrayList<>();
        loreStoneSword.add(ChatColor.GRAY + "Cost: 1 " + ChatColor.GOLD + "Salmon");
        ItemStack stoneSword = new ItemStack(Material.STONE_SWORD);
        ItemMeta stoneSwordMeta = stoneSword.getItemMeta();
        stoneSwordMeta.setLore(loreStoneSword);
        stoneSword.setItemMeta(stoneSwordMeta);
        shopgui.setItem(11,stoneSword);

        // Fireball
        List loreFireball = new ArrayList<>();
        loreFireball.add(ChatColor.GRAY + "Cost: 5 " + ChatColor.GOLD + "Salmon");
        ItemStack fireball = new ItemStack(Material.FIRE_CHARGE);
        ItemMeta fireballMeta = fireball.getItemMeta();
        fireballMeta.setLore(loreFireball);
        fireballMeta.setDisplayName(ChatColor.GOLD + "Fireball");
        fireball.setItemMeta(fireballMeta);
        shopgui.setItem(12,fireball);

        player.openInventory(shopgui);
    }
}