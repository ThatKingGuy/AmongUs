package com.gabe.amongus.maps;

import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Game {
    private final Map map;
    private Set<Player> players;

    public Game(Map map){
        this.map = map;
        players = new HashSet<>();
    }

    public Set<Player> getPlayers(){
        return Collections.unmodifiableSet(players);
    }

    public Map getMap(){
        return map;
    }


}
