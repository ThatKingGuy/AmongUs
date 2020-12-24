package com.gabe.amongus.managers;

import com.gabe.amongus.Amongus;
import com.gabe.amongus.maps.Map;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class VentManager implements Listener {
    private Set<Player> players = new HashSet<>();
    private HashMap<Player, Location> pos1 = new HashMap<>();
    private HashMap<Player, Location> pos2 = new HashMap<>();

    public void addPlayer(Player player) {
        players.add(player);
        player.getInventory().clear();

        player.getInventory().setItem(8, getExitItem());
        player.getInventory().setItem(0, getVent1());
        player.getInventory().setItem(1, getVent2());

    }

    public void removePlayer(Player player) {
        players.remove(player);
        player.getInventory().clear();
    }

    public ItemStack getExitItem() {
        ItemStack item = new ItemStack(Material.RED_TERRACOTTA);
        ItemMeta im = item.getItemMeta();
        im.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Exit");
        item.setItemMeta(im);
        return item;
    }

    public ItemStack getVent1() {
        ItemStack item = new ItemStack(Material.PINK_TERRACOTTA);
        ItemMeta im = item.getItemMeta();
        im.setDisplayName(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Vent Pos 1");
        item.setItemMeta(im);
        return item;
    }

    public ItemStack getVent2() {
        ItemStack item = new ItemStack(Material.PINK_TERRACOTTA);
        ItemMeta im = item.getItemMeta();
        im.setDisplayName(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Vent Pos 2");
        item.setItemMeta(im);
        return item;
    }

    public ItemStack getConfirm() {
        ItemStack item = new ItemStack(Material.GREEN_TERRACOTTA);
        ItemMeta im = item.getItemMeta();
        im.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Create Vent");
        item.setItemMeta(im);
        return item;
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event) {
        if (players.contains(event.getPlayer())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onItemPickUp(PlayerPickupItemEvent event) {
        if (players.contains(event.getPlayer())) {
            event.setCancelled(true);
        }
    }

    public void openMapMenu(Player player) {
        Inventory inv = Bukkit.createInventory(null, 45, ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "Map Selector");
        for (Map map : Amongus.getMapManager().getMaps()) {
            ItemStack item = new ItemStack(Material.BEDROCK);
            ItemMeta im = item.getItemMeta();
            im.setDisplayName(ChatColor.AQUA + "" + ChatColor.BOLD + map.getName());
            item.setItemMeta(im);
            inv.addItem(item);
        }
        player.openInventory(inv);
    }

    @EventHandler
    public void onInvClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (event.getView().getTitle().equals(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "Map Selector")) {
            event.setCancelled(true);
            ItemStack item = event.getCurrentItem();
            Bukkit.getLogger().info(item.getType().toString());
            if (item == null) {
                return;
            }
            if (item.getItemMeta() == null) {
                return;
            }
            if (item.getItemMeta().getDisplayName() == null) {
                return;
            }
            String name = ChatColor.stripColor(item.getItemMeta().getDisplayName());
            Bukkit.getLogger().info(name);
            if (Amongus.getMapManager().getMap(name) != null) {
                Amongus.getMapManager().getMap(name).addVent(pos1.get(player), pos2.get(player));
                pos1.remove(player);
                pos2.remove(player);
                player.closeInventory();
                player.getInventory().clear();
                player.sendMessage(MessageManager.added_vent.replaceAll("%map%", name));
            }
        }
    }

    @EventHandler
    public void itemClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (players.contains(player)) {
            event.setCancelled(true);
            if (event.getAction().toString().contains("RIGHT_CLICK")) {
                if (event.getItem() != null) {

                    event.setCancelled(true);
                    if (event.getItem().getItemMeta().getDisplayName().equals(getExitItem().getItemMeta().getDisplayName())) {
                        removePlayer(player);
                        player.sendMessage(MessageManager.exit_vent_mode);
                    }
                    if (event.getItem().getItemMeta().getDisplayName().equals(getVent1().getItemMeta().getDisplayName())) {
                        pos1.put(player, event.getClickedBlock().getLocation());
                        player.sendMessage(MessageManager.vent_pos1.replaceAll("%pos_x%",event.getClickedBlock().getX()+"").replaceAll("%pos_y%",event.getClickedBlock().getY()+"").replaceAll("%pos_z%",event.getClickedBlock().getZ()+""));
                    }
                    if (event.getItem().getItemMeta().getDisplayName().equals(getVent2().getItemMeta().getDisplayName())) {
                        pos2.put(player, event.getClickedBlock().getLocation());
                        player.sendMessage(MessageManager.vent_pos2.replaceAll("%pos_x%",event.getClickedBlock().getX()+"").replaceAll("%pos_y%",event.getClickedBlock().getY()+"").replaceAll("%pos_z%",event.getClickedBlock().getZ()+""));

                    }
                    if (event.getItem().getItemMeta().getDisplayName().equals(getConfirm().getItemMeta().getDisplayName())) {
                        removePlayer(player);
                        openMapMenu(player);
                    }

                    if (pos1.get(player) != null && pos2.get(player) != null) {
                        player.getInventory().setItem(2, getConfirm());
                    }
                }
            }
        }
    }
}
