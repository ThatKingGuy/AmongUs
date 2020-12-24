package com.gabe.amongus.maps;

import com.gabe.amongus.Amongus;
import com.gabe.amongus.managers.MessageManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Game {
    private final Map map;
    private Set<Player> players;
    private int countdown = -1;
    private int minplayers = 2;
    public int maxplayers = 8;
    private Amongus plugin;


    public Game(Map map, Amongus plugin){
        this.map = map;
        players = new HashSet<>();
        this.plugin = plugin;
    }

    public Set<Player> getPlayers(){
        return Collections.unmodifiableSet(players);
    }

    public Map getMap(){
        return map;
    }

    public void updateScoreboards(){

    }

    public void startGame(){

    }

    public void broadcastTitle(String title) {
        for (Player p : players) {
            p.sendTitle(ChatColor.translateAlternateColorCodes('&', title), "", 1, 20, 1);
        }
    }

    public void broadcast(String string){
        for(Player player : players){
            player.sendMessage(string);
        }
    }

    public void startCountDown() {
        new BukkitRunnable() {
            public void run() {
                updateScoreboards();
                if (players.size()>=minplayers) {
                    if (countdown == -1) {
                        countdown = 20;
                    } else if (countdown == 0) {
                        countdown = -1;
                        startGame();
                        cancel();
                    } else {
                        countdown--;

                    }
                    if (countdown != -1) {
                        for (Player player : players) {
                            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
                        }
                        String color;
                        if (countdown % 5 == 0 && countdown > 10) {
                            color = "&a";
                            broadcastTitle(color + countdown);

                        } else if (countdown < 11 && countdown >= 6) {
                            color = "&e";
                            broadcastTitle(color + countdown);

                        } else if (countdown < 6) {
                            color = "&c";
                            broadcastTitle(color + countdown);

                        }

                    }
                } else {
                    broadcast(MessageManager.canceling_game.replaceAll("%players%", players.size()+"").replaceAll("%minplayers%", minplayers+""));
                    countdown = -1;
                    updateScoreboards();
                    cancel();
                }

                //Use cancel(); if you want to close this repeating task.
            }
        }.runTaskTimer(plugin, 0L, 20L);
    }

    public void addPlayer(Player player){
        if(players.size() < maxplayers) {
            players.add(player);
            if(players.size()>=minplayers){
                startCountDown();
            }
        }
    }

}
