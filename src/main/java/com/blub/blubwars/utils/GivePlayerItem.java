package com.blub.blubwars.utils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GivePlayerItem {

    public GivePlayerItem(Player player, ItemStack cost, ItemStack get, int costAmount, int getAmount){
        if (player.getInventory().contains(cost.getType(),costAmount)){
            for (int i = 0; i < getAmount; i++){
                player.getInventory().addItem(get);
            }
            for (int i = 0; i < player.getInventory().getSize(); i++){
                ItemStack itemStack = player.getInventory().getItem(i);
                if (itemStack != null && itemStack.getType().equals(cost.getType())){
                    itemStack.setAmount(itemStack.getAmount()-costAmount);
                    player.updateInventory();
                    break;
                }
            }
        } else {
            player.sendMessage(ChatColor.RED + "You do not have enough materials to purchase this!");
        }
    }
}