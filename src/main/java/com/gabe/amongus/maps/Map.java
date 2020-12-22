package com.gabe.amongus.maps;

import com.gabe.amongus.CuboidSelection;
import com.gabe.amongus.enums.Room;
import com.gabe.amongus.enums.TaskType;
import org.bukkit.Location;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Map {
    private HashMap<Location, TaskType> tasks;

    public HashMap<Location, Location> getVents() {
        return vents;
    }

    private HashMap<Location, Location> vents;
    private HashMap<CuboidSelection, Room> roomSelections;
    private Location button;
    private Set<Location> spawns;
    private final String name;

    public Map(String name) {
        this.name = name;
        tasks = new HashMap<>();
        vents = new HashMap<>();
        roomSelections = new HashMap<>();
        spawns = new HashSet<>();
    }

    public void addTask(Location location, TaskType task){
        tasks.put(location, task);
    }
    public void addVent(Location location1, Location location2){
        vents.put(location1, location2);
    }

    public HashMap<Location, TaskType> getTasks(){
        return tasks;
    }
    public HashMap<CuboidSelection, Room> getRooms(){
        return roomSelections;
    }

    public void addRoom(CuboidSelection selection, Room room){
        roomSelections.put(selection, room);
    }

    public void setButton(Location location){
        button = location;
    }

    public Location getButton(){
        return button;
    }

    public void addSpawn(Location location){
        spawns.add(location);
    }

    public Set<Location> getSpawns(){
        return Collections.unmodifiableSet(spawns);
    }


    public String getName(){
        return name;
    }
}
