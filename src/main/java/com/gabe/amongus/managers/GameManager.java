package com.gabe.amongus.managers;

import com.gabe.amongus.Amongus;
import com.gabe.amongus.maps.Game;
import com.gabe.amongus.maps.Map;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class GameManager {
    private Set<Game> games = new HashSet<>();
    private final Amongus plugin;
    public GameManager(Amongus plugin){
        this.plugin = plugin;
    }

    public void reloadGames(){
        games = new HashSet<>();
        Bukkit.getLogger().info("MAPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPP");
        for(Map map : Amongus.getMapManager().getMaps()){
            Bukkit.getLogger().info("MAPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPP");
            addGame(new Game(map, plugin));
        }
    }

    public void addGame(Game map){
        games.add(map);
    }

    public void removeGame(Game map){
        games.remove(map);
    }

    public Set<Game> getMaps(){
        return Collections.unmodifiableSet(games);
    }

    public Game getGame(Player player){
        for(Game game : games){
            if(game.getPlayers().contains(player)){
                return game;
            }
        }
        return null;
    }
    public Game getGame(String string){
        for(Game game : games){
            if(game.getMap().getName().equals(string)){
                return game;
            }
        }
        return null;
    }
}
