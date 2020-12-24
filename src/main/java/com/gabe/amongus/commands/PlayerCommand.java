package com.gabe.amongus.commands;

import com.gabe.amongus.Amongus;
import com.gabe.amongus.enums.Room;
import com.gabe.amongus.enums.TaskType;
import com.gabe.amongus.managers.MessageManager;
import com.gabe.amongus.maps.Game;
import com.gabe.amongus.maps.Map;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlayerCommand implements CommandExecutor {
    private final Amongus plugin;
    String help;

    public PlayerCommand(Amongus plugin){
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
                if (args[0].equalsIgnoreCase("play")) {
                    Bukkit.getLogger().info(Amongus.getGameManager().getMaps().size()+"");
                    for(Game game : Amongus.getGameManager().getMaps()){
                        if(game.getPlayers().size() < game.maxplayers){
                            game.addPlayer(player);
                            return true;
                        }
                    }
                }
            }
        }else{
            sender.sendMessage(MessageManager.not_player);
        }
        return true;
    }
}
