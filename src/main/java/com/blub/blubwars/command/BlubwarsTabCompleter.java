package com.blub.blubwars.command;

import com.blub.blubwars.Blubwars;
import com.blub.blubwars.instance.Arena;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class BlubwarsTabCompleter implements TabCompleter {

    private Blubwars blubwars;

    public BlubwarsTabCompleter(Blubwars blubwars) {
        this.blubwars = blubwars;
    }

    List<String> result = new ArrayList<>();

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        switch (args.length){
            case 1:
                result.clear();
                result.add("help");
                result.add("join");
                result.add("leave");
                result.add("list");
                result.add("team");
                if (sender.hasPermission("blubwars.admin")){
                    result.add("admin");
                }
                break;
            case 2:
                switch (args[0]){
                    case "join":
                        result.clear();
                        for (Arena arena : blubwars.getArenaManager().getArenas()){
                            result.add(Integer.toString(arena.getId()));
                        }
                        break;
                    case "admin":
                        result.clear();
                        if (sender.hasPermission("blubwars.admin")){
                            result.add("help");
                            result.add("start");
                            result.add("stop");
                            result.add("list");
                        }
                        break;
                }
            case 3:
                switch (args[1]){
                    case "start":
                    case "stop":
                        result.clear();
                        for (Arena arena : blubwars.getArenaManager().getArenas()){
                            result.add(Integer.toString(arena.getId()));
                        }
                        break;
                }
        }
        return result;
    }
}
