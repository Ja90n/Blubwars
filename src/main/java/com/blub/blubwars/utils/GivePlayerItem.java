package com.blub.blubwars.utils;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GivePlayerItem {

    private Player player;
    private ItemStack cost,get;
    private int costAmount,getAmount;

    public GivePlayerItem(Player player, ItemStack cost, int costAmount){
        this.player = player;
        this.cost = cost;
        this.get = get;
        this.costAmount = costAmount;
        this.getAmount = getAmount;

    }

    public void giveItem(ItemStack get, int getAmount){
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

    public void giveArmor(String type){
        if (player.getInventory().contains(cost.getType(),costAmount)) {
            switch (type){
                case "iron":
                    player.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
                    player.getInventory().setLeggings(new ItemStack(Material.IRON_BOOTS));
                    break;
                case "diamond":
                    player.getInventory().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
                    player.getInventory().setLeggings(new ItemStack(Material.DIAMOND_BOOTS));
                    break;
            }
            for (int i = 0; i < player.getInventory().getSize(); i++){
                ItemStack itemStack = player.getInventory().getItem(i);
                if (itemStack != null && itemStack.getType().equals(cost.getType())){
                    itemStack.setAmount(itemStack.getAmount()-costAmount);
                    player.updateInventory();
                    break;
                }
            }
        }
    }
}