package com.blub.blubwars.command;

import com.blub.blubwars.Blubwars;
import com.blub.blubwars.GameState;
import com.blub.blubwars.instance.Arena;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ArenaCommand implements CommandExecutor {

    private Blubwars blubwars;
    public ArenaCommand(Blubwars blubwars) {
        this.blubwars = blubwars;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player){
            Player p = (Player) sender;
            if (args.length == 1 && args[0].equalsIgnoreCase("list")){
                p.sendMessage(ChatColor.BLUE + "These are the avalible arenas:");
                for (Arena arena : blubwars.getArenaManager().getArenas()){
                    p.sendMessage(ChatColor.BLUE + "- " + arena.getId() + "(" + arena.getState().name() + ")");
                }
            } else if (args.length == 1 && args[0].equalsIgnoreCase("leave")) {
                Arena arena = blubwars.getArenaManager().getArena(p);
                if (arena != null){
                    p.sendMessage(ChatColor.BLUE + "You have left the arena!");
                    arena.removePlayer(p);
                } else {
                    p.sendMessage(ChatColor.RED + "You are not in a arena");
                }
            } else if (args.length == 2 && args[0].equalsIgnoreCase("join")){
                if (blubwars.getArenaManager().getArena(p) != null){
                    p.sendMessage(ChatColor.RED + "You are already in a arena!");
                    return false;
                }
                int id;
                try {
                    id = Integer.parseInt(args[1]);
                } catch (NumberFormatException e){
                    p.sendMessage(ChatColor.RED + "You need to fill in a number!");
                    return false;
                }
                if (id >= 0 && id < blubwars.getArenaManager().getArenas().size()) {
                    Arena arena = blubwars.getArenaManager().getArena(id);
                    if (arena.getState().equals(GameState.RECRUITING) || arena.getState().equals(GameState.LIVE)){
                        p.sendMessage(ChatColor.BLUE + "You are now playing in arena " + arena.getId() + "!");
                        arena.addPlayer(p);
                    } else {
                        p.sendMessage(ChatColor.RED + "Game is already active!");
                    }
                } else {
                    p.sendMessage(ChatColor.RED + "This arena doesn't exist");
                }
            } else {
                p.sendMessage(ChatColor.RED + "Invalid usage");
            }
        }
        return false;
    }
}
