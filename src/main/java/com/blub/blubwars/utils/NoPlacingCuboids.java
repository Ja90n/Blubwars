package com.blub.blubwars.utils;

import com.blub.blubwars.enums.Team;
import com.blub.blubwars.instance.Arena;
import com.blub.blubwars.instance.Cuboid;
import com.blub.blubwars.manager.ConfigManager;
import java.util.HashMap;

public class NoPlacingCuboids {

    private Arena arena;
    private HashMap<Cuboid,String> arenaCuboids;

    public NoPlacingCuboids(Arena arena){
        this.arena = arena;
        this.arenaCuboids = new HashMap<>();
    }

    public void CreateCuboids(){
        for (Team team : arena.getTeams().values()){
            Cuboid playerIsland = new Cuboid(
                    ConfigManager.getPosition(arena,1,team),
                    ConfigManager.getPosition(arena,2,team)
            );
            arenaCuboids.put(playerIsland,team.getTeamName());
        }
    }
    public HashMap<Cuboid,String> getCuboids(){ return arenaCuboids; }
}