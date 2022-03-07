package com.blub.blubwars.team;

import com.blub.blubwars.Blubwars;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class TeamGui {

    public TeamGui(Player player, Blubwars blubwars){

        if (blubwars.getArenaManager().getArena(player) != null){
            Inventory gui = Bukkit.createInventory(null, 9, ChatColor.LIGHT_PURPLE + "Team Selection");
            for (Team team : Team.values()){
                ItemStack is = new ItemStack(team.getMaterial());
                ItemMeta isMeta = is.getItemMeta();
                isMeta.setDisplayName(team.getDisplay() + " " + ChatColor.GRAY + "(" + blubwars.getArenaManager().getArena(player).getTeamCount(team) + " players)");
                isMeta.setLocalizedName(team.name());
                is.setItemMeta(isMeta);

                gui.addItem(is);
            }
            player.openInventory(gui);
        } else {
            player.sendMessage(ChatColor.RED + "You are currently not in a game!");
        }
    }
}
