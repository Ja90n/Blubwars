package com.blub.blubwars.listener;

import com.blub.blubwars.Blubwars;
import com.blub.blubwars.enums.Team;
import com.blub.blubwars.instance.Arena;
import com.blub.blubwars.instance.Cuboid;
import com.blub.blubwars.manager.ConfigManager;
import com.blub.blubwars.runnable.item.SuckerRunnable;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;
import org.spigotmc.event.entity.EntityDismountEvent;

public class ItemListeners implements Listener {

    private Blubwars blubwars;
    int timeRun = 0;

    public ItemListeners(Blubwars blubwars) {
        this.blubwars = blubwars;
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent e){
        if (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
            Player player = e.getPlayer();
            if (player.getInventory().getItemInMainHand().getType().equals(Material.FIRE_CHARGE)){
                Fireball fireball = player.launchProjectile(Fireball.class);
                fireball.setVelocity(player.getLocation().getDirection().multiply(2));
                fireball.setIsIncendiary(false);
                player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount()-1);
            } else if (player.getInventory().getItemInMainHand().getType().equals(Material.FEATHER)){
                Chicken chicken = (Chicken) player.getWorld().spawnEntity(player.getLocation(), EntityType.CHICKEN);
                chicken.addPassenger(player);
                chicken.setCustomName("Parachute");
                player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount()-1);
            } else if (player.getInventory().getItemInMainHand().getType().equals(Material.LEAD)){
                if (blubwars.getArenaManager().getArena(player) != null){
                    if (blubwars.getArenaManager().getArena(player).getCatUUID(blubwars.getArenaManager().getArena(player).getTeam(player)) != null){
                        Cat cat = (Cat) Bukkit.getEntity(blubwars.getArenaManager().getArena(player).getCatUUID(blubwars.getArenaManager().getArena(player).getTeam(player)));
                        if (cat.getOwner() != player){
                            cat.setOwner(player);
                            cat.teleport(player.getLocation());
                            cat.setSitting(false);
                            player.sendMessage(ChatColor.AQUA+ "You have claimed the cat");
                            player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount()-1);
                        }  else {
                            player.sendMessage(ChatColor.RED + "You already have the cat!");
                        }
                    } else {
                        player.sendMessage(ChatColor.RED + "The cat is respawning!");
                    }
                }
            }
        } else if (e.getAction().equals(Action.LEFT_CLICK_AIR) || e.getAction().equals(Action.LEFT_CLICK_BLOCK)){
            if (!e.getPlayer().getPassengers().isEmpty()){
                Entity target = e.getPlayer().getPassengers().get(0);
                e.getPlayer().removePassenger(target);
                target.setVelocity(e.getPlayer().getLocation().getDirection().multiply(2).setY(2));
            }
        }
    }

    @EventHandler
    public void onRightClickAtEntity(PlayerInteractEntityEvent e){
        if (blubwars.getArenaManager().getArena(e.getPlayer()) != null){
            Player player = e.getPlayer();
            if (e.getRightClicked() instanceof Player){
                Player target = (Player) e.getRightClicked();
                if (player.getInventory().getItemInMainHand().getType().equals(Material.SADDLE)){
                    player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount()-1);
                    target.addPassenger(player);
                }
            }
        }
    }

    @EventHandler
    public void onProjectalHit(ProjectileHitEvent e){
        if (e.getEntity().getType().equals(EntityType.SNOWBALL)){
            new SuckerRunnable(e.getEntity().getLocation(),e.getEntity().getNearbyEntities(15,15,15),blubwars).start();
        }
    }

    @EventHandler
    public void onBlockExplode(BlockExplodeEvent e){
        for (Arena arena : blubwars.getArenaManager().getArenas()){
            for (Team team : Team.values()){
                Cuboid teamBase = new Cuboid(ConfigManager.getPosition(arena,1,team),ConfigManager.getPosition(arena,2,team));
                for (Block block : e.blockList()){
                    if (teamBase.contains(block)){
                       e.setCancelled(true);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onTntPlace(BlockPlaceEvent e){
        if (e.getBlock().getType().equals(Material.TNT)){
            e.getBlock().setType(Material.AIR);
            e.getBlock().getWorld().spawnEntity(e.getBlock().getLocation(),EntityType.PRIMED_TNT);
        }
    }

    @EventHandler
    public void onEntityDismount(EntityDismountEvent e){
        if (e.getDismounted().getType().equals(EntityType.CHICKEN)){
            e.getDismounted().remove();
        }
    }

    @EventHandler
    public void onExplosion(EntityExplodeEvent e){
        for (Arena arena : blubwars.getArenaManager().getArenas()){
            if (e.getEntity().getWorld().equals(arena.getWorld())){
                if (e.getEntity().getType().equals(EntityType.FIREBALL)){
                    for (Entity entity : e.getEntity().getNearbyEntities(5,5,5)){
                        Vector vector = entity.getLocation().toVector().subtract(e.getLocation().toVector()).multiply(5);
                        entity.setVelocity(vector);
                    }
                }
            }
            for (Team team : Team.values()){
                Cuboid teamBase = new Cuboid(ConfigManager.getPosition(arena,1,team),ConfigManager.getPosition(arena,2,team));
                for (Block block : e.blockList()){
                    if (teamBase.contains(block)){
                        e.setCancelled(true);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onInventoryClick (InventoryClickEvent e){
        if (e.getWhoClicked() instanceof Player){
            if (e.getSlotType() == InventoryType.SlotType.ARMOR){
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e){
        if (e.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_EXPLOSION)){
            e.setCancelled(true);
        } if (e.getCause().equals(EntityDamageEvent.DamageCause.FALL)){
            e.setDamage(e.getDamage()/5);
        }
    }
}