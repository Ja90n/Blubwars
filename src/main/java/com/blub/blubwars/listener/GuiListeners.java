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
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

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
                switch (e.getCurrentItem().getType()){
                    case RED_WOOL:
                        new GivePlayerItem(player,new ItemStack(Material.COD),1)
                                .giveItem(new ItemStack(Material.RED_WOOL),4);
                        break;
                    case BLUE_WOOL:
                        new GivePlayerItem(player,new ItemStack(Material.COD),1)
                                .giveItem(new ItemStack(Material.BLUE_WOOL),4);
                        break;
                    case GREEN_WOOL:
                        new GivePlayerItem(player,new ItemStack(Material.COD),1)
                                .giveItem(new ItemStack(Material.GREEN_WOOL),4);
                        break;
                    case PINK_WOOL:
                        new GivePlayerItem(player,new ItemStack(Material.COD),1)
                                .giveItem(new ItemStack(Material.PINK_WOOL),4);
                        break;
                    case RED_CONCRETE:
                        new GivePlayerItem(player,new ItemStack(Material.COD),1)
                                .giveItem(new ItemStack(Material.RED_CONCRETE),2);
                        break;
                    case BLUE_CONCRETE:
                        new GivePlayerItem(player,new ItemStack(Material.COD),1)
                                .giveItem(new ItemStack(Material.BLUE_CONCRETE),2);
                        break;
                    case GREEN_CONCRETE:
                        new GivePlayerItem(player,new ItemStack(Material.COD),1)
                                .giveItem(new ItemStack(Material.GREEN_CONCRETE),2);
                        break;
                    case PINK_CONCRETE:
                        new GivePlayerItem(player,new ItemStack(Material.COD),1)
                                .giveItem(new ItemStack(Material.PINK_CONCRETE),2);
                        break;
                    case RED_STAINED_GLASS:
                        new GivePlayerItem(player,new ItemStack(Material.SALMON),2)
                                .giveItem(new ItemStack(Material.RED_STAINED_GLASS),1);
                        break;
                    case BLUE_STAINED_GLASS:
                        new GivePlayerItem(player,new ItemStack(Material.COD),2)
                                .giveItem(new ItemStack(Material.BLUE_STAINED_GLASS),1);
                        break;
                    case GREEN_STAINED_GLASS:
                        new GivePlayerItem(player,new ItemStack(Material.COD),2)
                                .giveItem(new ItemStack(Material.GREEN_STAINED_GLASS),1);
                        break;
                    case PINK_STAINED_GLASS:
                        new GivePlayerItem(player,new ItemStack(Material.COD),2)
                                .giveItem(new ItemStack(Material.PINK_STAINED_GLASS),1);
                        break;
                    case STONE_SWORD:
                        new GivePlayerItem(player,new ItemStack(Material.COD),
                                10).giveItem(new ItemStack(Material.STONE_SWORD),1);
                        break;
                    case IRON_SWORD:
                        new GivePlayerItem(player,new ItemStack(Material.SALMON),
                                5).giveItem(new ItemStack(Material.IRON_SWORD),1);
                        break;
                    case DIAMOND_SWORD:
                        new GivePlayerItem(player,new ItemStack(Material.TROPICAL_FISH),
                                3).giveItem(new ItemStack(Material.DIAMOND_SWORD),1);
                    case CHAINMAIL_LEGGINGS:
                        new GivePlayerItem(player,new ItemStack(Material.COD),40).giveArmor("chain");
                        break;
                    case IRON_LEGGINGS:
                        new GivePlayerItem(player,new ItemStack(Material.SALMON),10).giveArmor("iron");
                        break;
                    case DIAMOND_LEGGINGS:
                        new GivePlayerItem(player,new ItemStack(Material.TROPICAL_FISH),5).giveArmor("diamond");
                        break;
                    case SHEARS:
                        ItemStack shears = new ItemStack(Material.SHEARS);
                        ItemMeta shearsMeta = shears.getItemMeta();
                        shearsMeta.setDisplayName(ChatColor.GRAY + "Shears");
                        shearsMeta.addEnchant(Enchantment.DIG_SPEED, 5,true);
                        shears.setItemMeta(shearsMeta);
                        new GivePlayerItem(player,new ItemStack(Material.COD),5).giveItem(shears,1);
                        break;
                    case STONE_PICKAXE:
                        new GivePlayerItem(player,new ItemStack(Material.COD),10)
                                .giveItem(new ItemStack(Material.STONE_PICKAXE),1);
                    case FIRE_CHARGE:
                        ItemStack fireball = new ItemStack(Material.FIRE_CHARGE);
                        ItemMeta fireballMeta = fireball.getItemMeta();
                        fireballMeta.setDisplayName(ChatColor.GOLD + "Fireball");
                        fireball.setItemMeta(fireballMeta);
                        new GivePlayerItem(player,new ItemStack(Material.SALMON), 5).giveItem(fireball,1);
                        break;
                    case FEATHER:
                        ItemStack feather = new ItemStack(Material.FEATHER);
                        ItemMeta featherMeta = feather.getItemMeta();
                        featherMeta.setDisplayName(ChatColor.GRAY + "Parachute");
                        feather.setItemMeta(featherMeta);
                        new GivePlayerItem(player,new ItemStack(Material.COD),10).giveItem(feather,1);
                        break;
                    case SNOWBALL:
                        ItemStack snowball = new ItemStack(Material.SNOWBALL);
                        ItemMeta snowballMeta = snowball.getItemMeta();
                        snowballMeta.setDisplayName(ChatColor.BLUE + "Sucker");
                        snowball.setItemMeta(snowballMeta);
                        new GivePlayerItem(player,new ItemStack(Material.TROPICAL_FISH),5).giveItem(snowball,1);
                        break;
                    case SPLASH_POTION:
                        ItemStack potion = new ItemStack(Material.SPLASH_POTION);
                        PotionMeta potionMeta = (PotionMeta) potion.getItemMeta();
                        potionMeta.addCustomEffect(new PotionEffect(PotionEffectType.HEAL,1,5),true);
                        potion.setItemMeta(potionMeta);
                        new GivePlayerItem(player,new ItemStack(Material.TROPICAL_FISH),1).giveItem(potion,1);
                        break;
                }
            }
        }
    }

    /*
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
                        shearsMeta.addEnchant(Enchantment.DIG_SPEED, 5,true);
                        shears.setItemMeta(shearsMeta);
                        new GivePlayerItem(player,new ItemStack(Material.COD),shears,5,1);
                        break;
                    case 14:
                        ItemStack feather = new ItemStack(Material.FEATHER);
                        ItemMeta featherMeta = feather.getItemMeta();
                        featherMeta.setDisplayName(ChatColor.GRAY + "Parachute");
                        feather.setItemMeta(featherMeta);
                        new GivePlayerItem(player,new ItemStack(Material.COD),feather,10,1);
                        break;
     */
}