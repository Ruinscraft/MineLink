package com.ruinscraft.minelink;

import com.ruinscraft.minelink.service.Service;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class LinkCommand implements CommandExecutor, TabCompleter {

    private MineLinkPlugin mineLink;

    public LinkCommand(MineLinkPlugin mineLink) {
        this.mineLink = mineLink;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }

        Player player = (Player) sender;

        if (args.length < 1) {
            player.sendMessage(ChatColor.RED + "/" + label + " <service>");
            return true;
        }

        Service service = mineLink.getServiceManager().getService(args[0]);

        if (service == null) {
            player.sendMessage(ChatColor.RED + "Service not found");
            return true;
        }

        service.link(player, mineLink);

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            for (Service service : mineLink.getServiceManager().getServices()) {
                if (service.getName().startsWith(args[0])) {
                    completions.add(service.getName());
                }
            }
        }

        return completions;
    }

}
