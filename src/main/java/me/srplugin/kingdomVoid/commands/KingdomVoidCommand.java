package me.srplugin.kingdomVoid.commands;

import me.srplugin.kingdomVoid.KingdomVoid;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class KingdomVoidCommand implements CommandExecutor {

    private final KingdomVoid plugin;

    public KingdomVoidCommand(KingdomVoid plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        if (args.length == 0){
            sender.sendMessage(ChatColor.RED + "Use /kingdomvoid <reload|info>");
            return true;
        }

        switch (args[0].toLowerCase()){
            case "reload":
                plugin.reloadConfig();
                sender.sendMessage(ChatColor.YELLOW + "The config.yml has been reloaded");
                break;

            case "info":
                sender.sendMessage(ChatColor.AQUA + "KingdomVoid was developed by SrCode");
                break;

            default:
                sender.sendMessage(ChatColor.RED + "Unknown command please use /kingdomvoid <reload|info>");
                break;
        }

        return true;

    }
}
