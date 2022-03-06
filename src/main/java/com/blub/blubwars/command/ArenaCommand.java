package com.blub.blubwars.command;

import com.blub.blubwars.Blubwars;
import com.blub.blubwars.GameState;
import com.blub.blubwars.instance.Arena;
import com.blub.blubwars.team.TeamGui;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public class ArenaCommand implements CommandExecutor {

    private Blubwars blubwars;
    private Player player;

    public ArenaCommand(Blubwars blubwars) {
        this.blubwars = blubwars;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        boolean bool = false;
        if (sender instanceof Player) {
            Player player = (Player) sender;
            this.player = player;
            String message = args[0].toLowerCase(Locale.ROOT);
            bool = handleCommand(message, args);
        }
        //It wil be always false? Why??
        return bool;
    }

    private boolean handleCommand(String message, String[] args) {
        if (message == "list") {
            getAllAreanasMessage();

        }
        if (message == "leave") {
            removePlayer();
        }
        if (message == "join") {
            joinCommand(message, args);

        }
        if (message == "team") {
            teamCommando(message, args);


        }
        if (message == "forcestop") {
            stopCommand(message, args);
        }
        else {
            sendMessage( ChatColor.RED + "Invalid usage");
        }

        return false;
    }

    private boolean stopCommand(String message, String args[]) {
        int id;
        try {
            id = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            sendMessage( ChatColor.RED + "You need to fill in a number!");
            return false;
        }
        if (id >= 0 && id < blubwars.getArenaManager().getArenas().size()) {
            Arena arena = blubwars.getArenaManager().getArena(id);
            if (arena.getState().equals(GameState.LIVE)) {
                arena.reset();
                sendMessage( ChatColor.BLUE + "You have force stopped " + id);
            } else {
                sendMessage( ChatColor.RED + "Game is not active!");
            }
        } else {
            sendMessage( ChatColor.RED + "This arena doesn't exist");
        }

        return false;
    }

    private boolean joinCommand(String message, String args[]) {
        if (blubwars.getArenaManager().getArena(player) != null) {
            sendMessage( ChatColor.RED + "You are already in a arena!");
            return false;
        }
        int id;
        try {
            id = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            sendMessage( ChatColor.RED + "You need to fill in a number!");
            return false;
        }
        if (id >= 0 && id < blubwars.getArenaManager().getArenas().size()) {
            Arena arena = blubwars.getArenaManager().getArena(id);
            if (arena.getState().equals(GameState.RECRUITING) || arena.getState().equals(GameState.COUNTDOWN)) {
                if (arena.getCanJoin()) {
                    sendMessage( ChatColor.BLUE + "You are now playing in arena " + arena.getId() + "!");
                    arena.addPlayer(player);
                } else {
                    sendMessage( ChatColor.RED + "You can't join right now. Map still loading!");
                }
            } else {
                sendMessage( ChatColor.RED + "Game is already active!");
            }
        } else {
            sendMessage( ChatColor.RED + "This arena doesn't exist");
        }
        return false;
    }

    private void teamCommando(String message, String args[]) {
        Arena arena = blubwars.getArenaManager().getArena(player);
        if (arena != null) {
            if (!(arena.getState() == GameState.LIVE)) {
                new TeamGui(arena, player);
            } else {
                sendMessage( ChatColor.RED + "You can not use this while the game is live");
            }
        } else {
            sendMessage( ChatColor.RED + "You are not in a arena");
        }
    }

    private void removePlayer() {
        Arena arena = blubwars.getArenaManager().getArena(player);
        if (arena == null) {
            sendMessage( ChatColor.RED + "You are not in a arena");
        } else {
            sendMessage( ChatColor.BLUE + "You have left the arena!");
            arena.removePlayer(player);
        }
    }

    private void sendMessage(@NotNull String message) {
        player.sendMessage(message);
    }

    private void getAllAreanasMessage() {
        sendMessage(ChatColor.BLUE + "These are the avalible arenas:");

        for (Arena arena : blubwars.getArenaManager().getArenas()) {
            player.sendMessage(ChatColor.BLUE + "- " + arena.getId() + "(" + arena.getState().name() + ")");
        }

    }
}
