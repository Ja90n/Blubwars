package com.blub.blubwars.utils;

import com.blub.blubwars.Blubwars;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class SetPlayerStartInventory {
    public SetPlayerStartInventory(Player player, Blubwars blubwars){
        Inventory inventory = player.getInventory();
        inventory.clear();
        inventory.setItem(0,new ItemStack(Material.WOODEN_SWORD));
        LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) new ItemStack(Material.LEATHER_BOOTS).getItemMeta();
        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
        ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);
        ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
        ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
        Color color = Color.WHITE;
        switch (blubwars.getArenaManager().getArena(player).getTeam(player)){
            case RED:
                color = Color.RED;
                break;
            case BLUE:
                color = Color.BLUE;
                break;
            case GREEN:
                color = Color.GREEN;
                break;
            case PINK:
                color = Color.FUCHSIA;
                break;
        }
        leatherArmorMeta.setColor(color);
        boots.setItemMeta(leatherArmorMeta);
        leggings.setItemMeta(leatherArmorMeta);
        chestplate.setItemMeta(leatherArmorMeta);
        helmet.setItemMeta(leatherArmorMeta);
        player.getInventory().setBoots(boots);
        player.getInventory().setLeggings(leggings);
        player.getInventory().setChestplate(chestplate);
        player.getInventory().setHelmet(helmet);
    }
}
