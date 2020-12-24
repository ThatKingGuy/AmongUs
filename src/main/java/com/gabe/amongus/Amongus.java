package com.gabe.amongus;

import com.gabe.amongus.commands.AdminCommand;
import com.gabe.amongus.commands.PlayerCommand;
import com.gabe.amongus.managers.GameManager;
import com.gabe.amongus.managers.MapManager;
import com.gabe.amongus.managers.SelectionManager;
import com.gabe.amongus.managers.VentManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Amongus extends JavaPlugin {
    private static GameManager gameManager;
    private static SelectionManager selectionManager;
    private static MapManager mapManager;
    private static VentManager ventManager;

    public static VentManager getVentManager() {
        return ventManager;
    }

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
        selectionManager = new SelectionManager();
        mapManager = new MapManager(this);
        ventManager = new VentManager();
        gameManager = new GameManager(this);
        getCommand("aua").setExecutor(new AdminCommand(this));
        getCommand("au").setExecutor(new PlayerCommand(this));
        Bukkit.getPluginManager().registerEvents(new GameListener(), this);
        Bukkit.getPluginManager().registerEvents(ventManager, this);
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
