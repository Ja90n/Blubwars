package com.blub.blubwars.gui;

import com.blub.blubwars.Blubwars;
import com.blub.blubwars.instance.Arena;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

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

        // Blocks

        // Wool
        ItemStack wool = new ItemStack(arena.getTeam(player).getMaterial());
        createItem(wool,null,ChatColor.GRAY + "Cost: 1 cod",10,4,null,0);

        // Concrete
        Material concreteMaterial = Material.WHITE_CONCRETE;
        switch (arena.getTeam(player)){
            case RED:
                concreteMaterial = Material.RED_CONCRETE;
                break;
            case BLUE:
                concreteMaterial = Material.BLUE_CONCRETE;
                break;
            case GREEN:
                concreteMaterial = Material.GREEN_CONCRETE;
                break;
            case PINK:
                concreteMaterial = Material.PINK_CONCRETE;
        }
        ItemStack concrete = new ItemStack(concreteMaterial);
        createItem(concrete,null,ChatColor.GRAY + "Cost: 1 cod",19,2,null,0);

        // Glass
        Material glassMaterial = Material.GLASS;
        switch (arena.getTeam(player)){
            case RED:
                glassMaterial = Material.RED_STAINED_GLASS;
                break;
            case BLUE:
                glassMaterial = Material.BLUE_STAINED_GLASS;
                break;
            case GREEN:
                glassMaterial = Material.GREEN_STAINED_GLASS;
                break;
            case PINK:
                glassMaterial = Material.PINK_STAINED_GLASS;
        }
        ItemStack glass = new ItemStack(glassMaterial);
        createItem(glass,null,ChatColor.GRAY + "Cost: 1 " + ChatColor.GOLD + "salmon",28,1,null,0);

        // Weapons

        // Stone sword
        createItem(new ItemStack(Material.STONE_SWORD),ChatColor.GRAY + "Stone sword",
                ChatColor.GRAY + "Cost: 10 cod",11,1,null,0);

        // Iron sword
        createItem(new ItemStack(Material.IRON_SWORD),ChatColor.GOLD + "Iron sword",
                ChatColor.GRAY + "Cost: 5 " + ChatColor.GOLD + "salmon",20,1,null,0);

        // Diamond sword
        createItem(new ItemStack(Material.DIAMOND_SWORD),ChatColor.BLUE + "Diamond sword",
                ChatColor.GRAY + "Cost: 3 " + ChatColor.BLUE + "tropical fish",29,1,null,0);

        // Armor

        // Chain
        createItem(new ItemStack(Material.CHAINMAIL_LEGGINGS),ChatColor.GRAY + "Chainmail armor",
                ChatColor.GRAY + "Cost: 15 cod", 12,1,null,0);

        // Iron
        createItem(new ItemStack(Material.IRON_LEGGINGS),ChatColor.GOLD + "Iron armor",
                ChatColor.GRAY + "Cost: 10 " + ChatColor.GOLD + "salmon", 21,1,null,0);

        // Diamond
        createItem(new ItemStack(Material.DIAMOND_LEGGINGS),ChatColor.BLUE + "Diamond armor",
                ChatColor.GRAY + "Cost: 5 " + ChatColor.BLUE + "tropical fish", 30,1,null,0);

        // Tools

        // Shears
        createItem(new ItemStack(Material.SHEARS),ChatColor.GRAY + "Shears",
                ChatColor.GRAY + "Cost: 5 " + "cod", 13,1,Enchantment.DIG_SPEED,5);

        // Pickaxe
        createItem(new ItemStack(Material.STONE_PICKAXE),ChatColor.GRAY + "Stone Pickaxe",
                ChatColor.GRAY + "Cost: 10 " + "cod", 22,1,null,0);

        //Misc

        // Fireball
        createItem(new ItemStack(Material.FIRE_CHARGE),ChatColor.GOLD + "Fireball",
                ChatColor.GRAY + "Cost: 5 " + ChatColor.GOLD + "salmon",15,1,null,0);

        // Parachute
        createItem(new ItemStack(Material.FEATHER),ChatColor.GRAY + "Parachute",
                ChatColor.GRAY + "Cost: 10 cod", 24, 1,null,0);

        // Tnt
        createItem(new ItemStack(Material.TNT),ChatColor.RED + "TNT",
                ChatColor.GRAY + "Cost: 20 cod",33,1,null,0);

        // Sucker
        createItem(new ItemStack(Material.SNOWBALL),ChatColor.BLUE +"Sucker", ChatColor.GRAY +
                "Cost: 5 " + ChatColor.BLUE + "tropical fish",16,1,null,0);

        // Cat Claimer
        createItem(new ItemStack(Material.LEAD),ChatColor.BLUE +"Cat Claimer", ChatColor.GRAY +
                "Cost: 15 " + ChatColor.BLUE + "tropical fish",25,1,null,0);

        // Health pot
        ItemStack potion = new ItemStack(Material.SPLASH_POTION);
        PotionMeta potionMeta = (PotionMeta) potion.getItemMeta();
        potionMeta.addCustomEffect(new PotionEffect(PotionEffectType.HEAL,1,5),true);
        potion.setItemMeta(potionMeta);
        createItem(potion,ChatColor.BLUE +"Heath potion", ChatColor.GRAY +
                "Cost: 1 " + ChatColor.BLUE + "tropical fish",34,1,null,0);

        player.openInventory(shopgui);
    }

    private void createItem(ItemStack itemStack, String displayName, String lore, int index,int amount,
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
        itemStack.setAmount(amount);
        itemStack.setItemMeta(itemMeta);
        shopgui.setItem(index, itemStack);
    }
}