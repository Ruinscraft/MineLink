package com.ruinscraft.minelink;

import com.ruinscraft.minelink.MineLinkPlugin;
import com.ruinscraft.minelink.service.Service;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LinkCommandExecutor implements CommandExecutor {

    private MineLinkPlugin mineLink;

    public LinkCommandExecutor(MineLinkPlugin mineLink) {
        this.mineLink = mineLink;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }

        Player player = (Player) sender;

        if (args.length < 1) {
            mineLink.getServiceManager().getServices().forEach(service -> {
                player.sendMessage(ChatColor.GOLD + service.getName());
            });
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

}
