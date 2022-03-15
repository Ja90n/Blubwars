package com.blub.blubwars.enums;

import org.bukkit.ChatColor;
import org.bukkit.Material;

public enum Team {

    RED(ChatColor.RED + "Red", Material.RED_WOOL,"red"),
    BLUE(ChatColor.BLUE + "Blue", Material.BLUE_WOOL,"blue"),
    GREEN(ChatColor.GREEN + "Green", Material.GREEN_WOOL,"green"),
    PINK(ChatColor.LIGHT_PURPLE + "Pink", Material.PINK_WOOL,"pink");

    private String display;
    private Material material;
    private String teamName;

    Team (String display, Material material, String teamName){
        this.display = display;
        this.material = material;
        this.teamName = teamName;

    }

    public String getDisplay() { return display; }
    public Material getMaterial() { return material; }
    public String getTeamName() { return teamName; }
}
