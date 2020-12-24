package com.gabe.amongus.commands;

import com.gabe.amongus.Amongus;
import com.gabe.amongus.enums.Room;
import com.gabe.amongus.enums.TaskType;
import com.gabe.amongus.maps.Map;
import com.gabe.amongus.managers.MessageManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AdminCommand implements CommandExecutor {
    private final Amongus plugin;
    String help;

    public AdminCommand(Amongus plugin){
        this.plugin = plugin;
         help = ChatColor.DARK_GRAY+"*-------------  "+ChatColor.AQUA+""+ChatColor.BOLD+"Among Us v"+plugin.getDescription().getVersion()+ChatColor.DARK_GRAY+" --------------*\n" +
                ChatColor.LIGHT_PURPLE+""+ ChatColor.BOLD + "/aua "+ChatColor.AQUA+""+ChatColor.BOLD+"create <name> "+ChatColor.DARK_GRAY+"- "+ChatColor.GRAY+""+ChatColor.BOLD+"Creates an arena\n" +
                ChatColor.LIGHT_PURPLE+""+ ChatColor.BOLD + "/aua "+ChatColor.AQUA+""+ChatColor.BOLD+"list "+ChatColor.DARK_GRAY+"- "+ChatColor.GRAY+""+ChatColor.BOLD+"Lists arenas\n" +
                ChatColor.LIGHT_PURPLE+""+ ChatColor.BOLD +"/aua "+ChatColor.AQUA+""+ChatColor.BOLD+"addtask <arena> <task> "+ChatColor.DARK_GRAY+"- "+ChatColor.GRAY+""+ChatColor.BOLD+"Adds a task \n" +
                ChatColor.LIGHT_PURPLE+""+ ChatColor.BOLD +"/aua "+ChatColor.AQUA+""+ChatColor.BOLD+"setbutton <arena> "+ChatColor.DARK_GRAY+"- "+ChatColor.GRAY+""+ChatColor.BOLD+"Sets the button\n" +
                ChatColor.LIGHT_PURPLE+""+ ChatColor.BOLD +"/aua "+ChatColor.AQUA+""+ChatColor.BOLD+"addspawn <arena> "+ChatColor.DARK_GRAY+"- "+ChatColor.GRAY+""+ChatColor.BOLD+"Adds a spawnpoint\n" +
                ChatColor.LIGHT_PURPLE+""+ ChatColor.BOLD +"/aua "+ChatColor.AQUA+""+ChatColor.BOLD+"setroom <arena> <room> "+ChatColor.DARK_GRAY+"- "+ChatColor.GRAY+""+ChatColor.BOLD+"Creates a room\n" +
                 ChatColor.LIGHT_PURPLE+""+ ChatColor.BOLD +"/aua "+ChatColor.AQUA+""+ChatColor.BOLD+"wand "+ChatColor.DARK_GRAY+"- "+ChatColor.GRAY+""+ChatColor.BOLD+"Gets the cuboid selection wand\n" +
                 ChatColor.LIGHT_PURPLE+""+ ChatColor.BOLD +"/aua "+ChatColor.AQUA+""+ChatColor.BOLD+"vent "+ChatColor.DARK_GRAY+"- "+ChatColor.GRAY+""+ChatColor.BOLD+"Goes into vent selection mode\n" +
                ChatColor.LIGHT_PURPLE+""+ ChatColor.BOLD +"/aua "+ChatColor.AQUA+""+ChatColor.BOLD+"save <arena> "+ChatColor.DARK_GRAY+"- "+ChatColor.GRAY+""+ChatColor.BOLD+"Saves the arena\n" +
                ChatColor.DARK_GRAY+"*------------------------------------------*";
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(args.length > 0) {
                if (args[0].equalsIgnoreCase("help")) {
                    player.sendMessage(help);
                }
                else if(args[0].equalsIgnoreCase("create")) {
                    if(args.length > 1) {
                        Amongus.getMapManager().addMap(new Map(args[1]));
                        player.sendMessage(MessageManager.create_command.replaceAll("%map%", args[1]));
                    }else{
                        player.sendMessage(MessageManager.invalid_create_command);
                    }
                }
                else if(args[0].equalsIgnoreCase("list")) {
                    player.sendMessage(ChatColor.GREEN+"Games:");
                    for(Map map : Amongus.getMapManager().getMaps()){
                        player.sendMessage(ChatColor.GREEN+map.getName());
                    }
                }
                else if(args[0].equalsIgnoreCase("save")) {
                    if(args.length > 1) {
                        if(Amongus.getMapManager().getMap(args[1]) != null){
                            Amongus.getMapManager().saveMap(Amongus.getMapManager().getMap(args[1]));
                            player.sendMessage(MessageManager.saved_map.replaceAll("%map%",args[1]));
                        }else{
                            player.sendMessage(MessageManager.invalid_map.replaceAll("%map%",args[1]));
                        }
                    }else{
                        player.sendMessage(MessageManager.invalid_save_command);
                    }
                }
                else if(args[0].equalsIgnoreCase("addtask")) {
                    if(args.length > 2) {
                        Map map = Amongus.getMapManager().getMap(args[1]);
                        if(map != null){
                            TaskType type = null;
                            for (TaskType t : TaskType.values()){
                                if(t.toString().equalsIgnoreCase(args[2])){
                                     type= t;
                                }
                            }
                            if(type==null){
                                player.sendMessage(MessageManager.invalid_task.replaceAll("%task%",args[2]));
                                return true;
                            }

                            map.addTask(player.getTargetBlockExact(6).getLocation(), type);
                            player.sendMessage(MessageManager.added_task.replaceAll("%map%", map.getName()).replaceAll("%task%", type.toString()));
                        }else{
                            player.sendMessage(MessageManager.invalid_map.replaceAll("%map%",args[1]));
                        }
                    }else{
                        player.sendMessage(MessageManager.invalid_task_command);
                    }
                }
                else if(args[0].equalsIgnoreCase("setbutton")) {
                    if(args.length > 1) {
                        if(Amongus.getMapManager().getMap(args[1]) != null){
                            Amongus.getMapManager().getMap(args[1]).setButton(player.getTargetBlockExact(6).getLocation());
                            player.sendMessage(MessageManager.set_button.replaceAll("%map%",args[1]));
                        }else{
                            player.sendMessage(MessageManager.invalid_map.replaceAll("%map%",args[1]));
                        }
                    }else{
                        player.sendMessage(MessageManager.invalid_save_command);
                    }
                }
                else if(args[0].equalsIgnoreCase("setroom")) {
                    if (args.length > 2) {
                        Map map = Amongus.getMapManager().getMap(args[1]);
                        if (map != null) {
                            Room room = null;
                            for (Room t : room.values()) {
                                if (t.toString().equalsIgnoreCase(args[2])) {
                                    room = t;
                                }
                            }
                            if (room == null) {
                                player.sendMessage(MessageManager.invalid_room.replaceAll("%room%", args[2]));
                                return true;
                            }

                            if(!Amongus.getSelectionManager().hasValidSelection(player)){
                                player.sendMessage(MessageManager.invalid_selection);
                                return true;
                            }
                            map.addRoom(Amongus.getSelectionManager().getSelection(player), room);

                            player.sendMessage(MessageManager.added_room.replaceAll("%map%", map.getName()).replaceAll("%room%", room.toString()));
                        } else {
                            player.sendMessage(MessageManager.invalid_map.replaceAll("%map%", args[1]));
                        }
                    } else {
                        player.sendMessage(MessageManager.invalid_room_command);
                    }
                }
                else if(args[0].equalsIgnoreCase("addspawn")) {
                    if (args.length > 1) {
                        Map map = Amongus.getMapManager().getMap(args[1]);
                        if (map != null) {
                            map.addSpawn(player.getLocation());
                            player.sendMessage(MessageManager.added_spawn.replaceAll("%map%", map.getName()));
                        } else {
                            player.sendMessage(MessageManager.invalid_map.replaceAll("%map%", args[1]));
                        }
                    } else {
                        player.sendMessage(MessageManager.invalid_spawn_command);
                    }
                }
                else if(args[0].equalsIgnoreCase("vent")) {
                    Amongus.getVentManager().addPlayer(player);
                    player.sendMessage(MessageManager.vent_mode);
                }
            }
        }else{
            sender.sendMessage(MessageManager.not_player);
        }
        return true;
    }
}
