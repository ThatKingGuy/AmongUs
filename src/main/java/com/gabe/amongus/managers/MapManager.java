package com.gabe.amongus.managers;

import com.gabe.amongus.Amongus;
import com.gabe.amongus.CuboidSelection;
import com.gabe.amongus.InvalidConfigurationLocation;
import com.gabe.amongus.enums.Room;
import com.gabe.amongus.enums.TaskType;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class MapManager {
    private Set<com.gabe.amongus.maps.Map> maps = new HashSet<>();
    private File gameYml;
    private FileConfiguration gameData;

    public MapManager(Amongus plugin){
        gameYml = new File(plugin.getDataFolder() + "/maps.yml");
        gameData = YamlConfiguration.loadConfiguration(gameYml);
    }

    public void addMap(com.gabe.amongus.maps.Map map){
        maps.add(map);
    }
    public Set<com.gabe.amongus.maps.Map> getMaps(){
        return Collections.unmodifiableSet(maps);
    }

    public void loadMaps() throws InvalidConfigurationLocation {
        for(String s : gameData.getKeys(false)){
            com.gabe.amongus.maps.Map map = new com.gabe.amongus.maps.Map(s);
            Bukkit.getLogger().info(s);

            if(gameData.getString(s+".button") != null){
                map.setButton(deserializeLoc(gameData.getString(s+".button")));
            }

            for(String task : gameData.getStringList(s+".tasks")){
                Location l = deserializeLoc(task.split(",")[0]);
                TaskType t = TaskType.valueOf(task.split(",")[1]);
                map.addTask(l,t);

            }

            for(String vent : gameData.getStringList(s+".vents")){
                Location l1 = deserializeLoc(vent.split(",")[0]);
                Location l2 = deserializeLoc(vent.split(",")[1]);
                map.addVent(l1,l2);

            }

            for(String spawn : gameData.getStringList(s+".spawns")){
                Location l = deserializeLoc(spawn);
                map.addSpawn(l);
            }
            maps.add(map);
        }
        Amongus.getGameManager().reloadGames();
    }

    public void saveMap(com.gabe.amongus.maps.Map map){
        gameData.set(map.getName()+".button", serialiseLoc(map.getButton()));

        List<String> spawns = new ArrayList<>();
        for(Location location : map.getSpawns()){
            spawns.add(serialiseLoc(location));
        }
        gameData.set(map.getName()+".spawns", spawns);

        List<String> tasks = new ArrayList<>();
        for(java.util.Map.Entry<Location, TaskType> entry : map.getTasks().entrySet()){
            tasks.add(serialiseLoc(entry.getKey()) + "," + entry.getValue().toString());
        }
        gameData.set(map.getName()+".tasks", tasks);

        List<String> rooms = new ArrayList<>();
        for(java.util.Map.Entry<CuboidSelection, Room> entry : map.getRooms().entrySet()){
            rooms.add(serialiseLoc(entry.getKey().pos1) + "," + serialiseLoc(entry.getKey().pos2) + "," + entry.getValue().toString());
        }
        gameData.set(map.getName()+".rooms", rooms);

        List<String> vents = new ArrayList<>();
        for(java.util.Map.Entry<Location, Location> entry : map.getVents().entrySet()){
            vents.add(serialiseLoc(entry.getKey()) + "," + serialiseLoc(entry.getValue()));
        }
        gameData.set(map.getName()+".vents", vents);

        saveMaps();
    }

    private void saveMaps() {
        try {
            gameData.save(gameYml);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private String serialiseLoc(Location location) {
        if (location != null) {
            String str = location.getWorld().getName() + ";" + location.getBlockX() + ";" + location.getBlockY() + ";" + location.getBlockZ();
            return str;
        } else {
            return null;
        }
    }

    private Location deserializeLoc(String str) throws InvalidConfigurationLocation {
        if(str == null){
            throw new InvalidConfigurationLocation("Location cant be null!");
        }
        String[] split = str.split(";");
        if (split.length == 4) {
            Location location = new Location(Bukkit.getWorld(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]), Integer.parseInt(split[3]));
            return location;
        } else {
            throw new InvalidConfigurationLocation("Invalid serialised location!");
        }
    }

    public com.gabe.amongus.maps.Map getMap(String name){
        for(com.gabe.amongus.maps.Map map : maps){
            if(map.getName().equals(name)){
                return map;
            }
        }
        return null;
    }
}
