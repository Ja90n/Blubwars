package com.blub.blubwars.command;

import com.blub.blubwars.Blubwars;
import org.bukkit.entity.Player;

public class GameWrapper {

    public Blubwars blubwars;
    public Player player;

    public GameWrapper(Blubwars blubwars, Player player) {
        this.blubwars= blubwars;
        this.player = player;
    }


}
