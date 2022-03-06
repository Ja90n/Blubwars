package com.blub.blubwars.team;

import org.bukkit.ChatColor;
import org.bukkit.Material;

public enum Team {

    RED(ChatColor.RED + "Red", Material.RED_CONCRETE),
    BLUE(ChatColor.BLUE + "Blue", Material.BLUE_CONCRETE),
    GREEN(ChatColor.GREEN + "Green", Material.GREEN_CONCRETE),
    PINK(ChatColor.LIGHT_PURPLE + "Pink", Material.PINK_CONCRETE);

    private String display;
    private Material material;

    Team (String display, Material material){
        this.display = display;
        this.material = material;
    }

    public String getDisplay() { return display; }
    public Material getMaterial() { return material; }
}
