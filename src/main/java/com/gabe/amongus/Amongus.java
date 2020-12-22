package com.gabe.amongus;

import com.gabe.amongus.commands.AdminCommand;
import com.gabe.amongus.managers.GameManager;
import com.gabe.amongus.managers.MapManager;
import com.gabe.amongus.managers.SelectionManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Amongus extends JavaPlugin {
    private static GameManager gameManager;
    private static SelectionManager selectionManager;
    private static MapManager mapManager;
    public static MapManager getMapManager(){
        return mapManager;
    }
    public static SelectionManager getSelectionManager(){
        return selectionManager;
    }
    public static GameManager getGameManager(){
        return gameManager;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        gameManager = new GameManager();
        selectionManager = new SelectionManager();
        mapManager = new MapManager(this);
        getCommand("aua").setExecutor(new AdminCommand(this));
        Bukkit.getPluginManager().registerEvents(new GameListener(), this);
        try {
            mapManager.loadMaps();
        } catch (InvalidConfigurationLocation invalidConfigurationLocation) {
            invalidConfigurationLocation.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
