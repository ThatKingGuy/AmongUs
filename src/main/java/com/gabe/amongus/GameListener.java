package com.gabe.amongus;

import com.gabe.amongus.maps.Game;
import com.gabe.amongus.managers.MessageManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class GameListener implements Listener {
    @EventHandler
    public void onInteractEvent(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if(event.getItem() != null){
            ItemStack item = event.getItem();
            if(item.getType() == Material.BLAZE_ROD){
                if(event.getPlayer().hasPermission("amongus.admin.select")){
                    if(event.getAction() == Action.LEFT_CLICK_BLOCK){
                        Amongus.getSelectionManager().setCorner1(event.getPlayer(), event.getClickedBlock().getLocation());
                        player.sendMessage(MessageManager.selected_pos1.replaceAll("%pos_x%",event.getClickedBlock().getX()+"").replaceAll("%pos_y%",event.getClickedBlock().getY()+"").replaceAll("%pos_z%",event.getClickedBlock().getZ()+""));
                        event.setCancelled(true);
                    }
                    if(event.getAction() == Action.RIGHT_CLICK_BLOCK){
                        Amongus.getSelectionManager().setCorner2(event.getPlayer(), event.getClickedBlock().getLocation());
                        player.sendMessage(MessageManager.selected_pos2.replaceAll("%pos_x%",event.getClickedBlock().getX()+"").replaceAll("%pos_y%",event.getClickedBlock().getY()+"").replaceAll("%pos_z%",event.getClickedBlock().getZ()+""));
                        event.setCancelled(true);
                    }
                }
            }

        }

        Game game = Amongus.getGameManager().getGame(event.getPlayer());
        if(event.getAction()== Action.RIGHT_CLICK_BLOCK){
            if(game != null){
                for(Location location : game.getMap().getTasks().keySet()){
                    if(isLoc(location, event.getClickedBlock().getLocation())){
                        Bukkit.getLogger().info("did task!");
                    }
                }
            }
        }
    }

    private boolean isLoc(Location loc1, Location loc2){
        if(loc1.getX() != loc2.getX()){
            return false;
        }
        if(loc1.getY() != loc2.getY()){
            return false;
        }
        if(loc1.getZ() != loc2.getZ()){
            return false;
        }
        if(loc1.getWorld() != loc2.getWorld()){
            return false;
        }

        return true;
    }
}
