package com.blub.blubwars.listener;

import com.blub.blubwars.Blubwars;
import com.blub.blubwars.instance.Arena;
import com.blub.blubwars.runnable.item.SuckerRunnable;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
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
    public void onEntityDismount(EntityDismountEvent e){
        if (e.getDismounted().getType().equals(EntityType.CHICKEN)){
            e.getDismounted().remove();
        }
    }

    @EventHandler
    public void onExplosion(EntityExplodeEvent e){
        for (Arena arena : blubwars.getArenaManager().getArenas()){
            if (e.getEntity().getWorld().equals(arena.getWorld())){
                for (Entity entity : e.getEntity().getNearbyEntities(5,5,5)){
                    Vector vector = entity.getLocation().toVector().subtract(e.getLocation().toVector()).multiply(5);
                    entity.setVelocity(vector);
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