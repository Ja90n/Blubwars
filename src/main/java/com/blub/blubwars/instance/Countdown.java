package com.blub.blubwars.instance;

import com.blub.blubwars.Blubwars;
import com.blub.blubwars.GameState;
import com.blub.blubwars.manager.configManager;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

public class Countdown extends BukkitRunnable {

    private Blubwars blubwars;
    private Arena arena;
    private int countdownSeconds;

    public Countdown(Blubwars blubwars, Arena arena){
        this.blubwars = blubwars;
        this.arena = arena;
        this.countdownSeconds = configManager.getCountdownSeconds()+1;
    }

    public void start(){
        arena.setState(GameState.COUNTDOWN);
        runTaskTimer(blubwars, 0, 20);
    }

    @Override
    public void run() {
        if (countdownSeconds == 0){
            cancel();
            arena.start();
            return;
        }

        if (countdownSeconds <= 10 || countdownSeconds % 15 == 0){
            arena.sendMessage(ChatColor.BLUE + "Game will start in " + countdownSeconds + " second" + (countdownSeconds == 1 ? "" : "s") + ".");
        }

        arena.sendTitle(ChatColor.GRAY + "Game is starting in ", ChatColor.LIGHT_PURPLE.toString() + countdownSeconds + " second" + (countdownSeconds == 1 ? "" : "s") + ".");

        countdownSeconds--;
    }

}
