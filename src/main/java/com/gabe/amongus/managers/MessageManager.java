package com.gabe.amongus.managers;

import org.bukkit.ChatColor;

public class MessageManager {
    public static String not_player = c("&cThis command can only be executed by players.");
    public static String no_perm = c("&cYou do not have permission to execute this command.");
    public static String invalid_admin_command = c("&cInvalid command. Try /awa help");
    public static String invalid_create_command = c("&cInvalid usage. Use /aua create <name>");
    public static String invalid_room_command = c("&cInvalid usage. Use /aua addroom <arena> <room>");
    public static String invalid_save_command = c("&cInvalid usage. Use /aua save <arena>");
    public static String invalid_task_command = c("&cInvalid usage. Use /aua addtask <arena> <task>");
    public static String invalid_spawn_command = c("&cInvalid usage. Use /aua addspawn  <arena>");
    public static String invalid_map = c("&cCouldn't find map named %map%");
    public static String invalid_task = c("&c%task% is not a valid task.");
    public static String invalid_room = c("&c%room% is not a valid room.");
    public static String added_task = c("&aAdded %task% to %map%");
    public static String added_room = c("&aAdded %room% to %map%");
    public static String added_spawn = c("&aAdded a spawn to %map%");
    public static String saved_map = c("&aSaved map named %map%");
    public static String set_button = c("&aSet %map%'s button.");
    public static String create_command = c("&aCreated game named %map%.");
    public static String invalid_selection = c("&cYour selection is invalid. Try /aua wand for the selection wand.");
    public static String selected_pos1 = c("&dSet pos 1 to (%pos_x%, %pos_y%, %pos_z%)");
    public static String selected_pos2 = c("&dSet pos 2 to (%pos_x%, %pos_y%, %pos_z%)");

    public static String c(String string){
        return ChatColor.translateAlternateColorCodes('&', string);
    }
}
