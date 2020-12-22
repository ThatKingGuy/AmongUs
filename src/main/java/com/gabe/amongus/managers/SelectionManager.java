package com.gabe.amongus.managers;

import com.gabe.amongus.CuboidSelection;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class SelectionManager {
    private HashMap<Player, Location> corner1s = new HashMap<>();
    private HashMap<Player, Location> corner2s = new HashMap<>();

    public boolean hasValidSelection(Player player){
        if(corner1s.get(player) == null){
            return false;
        }
        if(corner2s.get(player) == null){
            return false;
        }
        return true;
    }

    public CuboidSelection getSelection(Player player){
        CuboidSelection selection = new CuboidSelection(corner1s.get(player), corner2s.get(player));
        return selection;
    }

    public void setCorner1(Player player, Location location){
        corner1s.put(player, location);
    }

    public void setCorner2(Player player, Location location){
        corner2s.put(player, location);
    }
}
