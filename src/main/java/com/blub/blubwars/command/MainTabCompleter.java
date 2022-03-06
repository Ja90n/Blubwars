package com.blub.blubwars.command;

import com.blub.blubwars.Blubwars;
import com.blub.blubwars.instance.Arena;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class MainTabCompleter implements TabCompleter {

    private Blubwars blubwars;

    public MainTabCompleter(Blubwars blubwars) {
        this.blubwars = blubwars;
    }

    List<String> result = new ArrayList<>();

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1){
            result.add("help");
            result.add("join");
            result.add("leave");
            result.add("list");
        } else if (args.length == 2){
            if (args[1].equals("join")){
                for (Arena arena : blubwars.getArenaManager().getArenas()){
                    result.add(Integer.toString(arena.getId()));
                }
            } else if (args[1].equals("admin")) {

            }
        }
        return result;
    }
}
