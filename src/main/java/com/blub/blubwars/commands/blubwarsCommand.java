package com.blub.blubwars.commands;

import com.blub.blubwars.Blubwars;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.AnimalTamer;
import org.bukkit.entity.Cat;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class blubwarsCommand implements CommandExecutor {

    private Blubwars blubwars;
    public blubwarsCommand(Blubwars blubwars) {
        this.blubwars = blubwars;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player){
            Player p = (Player) sender;
            if (args.length == 1){
                switch (args[0]){
                    case "cat":
                        Cat cat = (Cat) p.getWorld().spawnEntity(p.getLocation(), EntityType.CAT);
                        cat.setCatType(Cat.Type.BLACK);
                        cat.setCollarColor(DyeColor.PINK);
                        cat.setCustomName(ChatColor.LIGHT_PURPLE + "Pink cat: " + ChatColor.BLUE + "9 lives");
                        cat.setOwner(p);
                        break;
                }
            }
        }
        return false;
    }
}