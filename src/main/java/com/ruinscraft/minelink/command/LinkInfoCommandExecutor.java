package com.ruinscraft.minelink.command;

import com.ruinscraft.minelink.LinkUser;
import com.ruinscraft.minelink.MineLinkPlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LinkInfoCommandExecutor implements CommandExecutor {

    private MineLinkPlugin mineLink;

    public LinkInfoCommandExecutor(MineLinkPlugin mineLink) {
        this.mineLink = mineLink;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        final LinkUser linkUser;

        if (args.length > 1) {
            if (args[0].startsWith("-")) {
                if (!(sender instanceof Player)) {
                    return false;
                }

                switch (args[0].toLowerCase()) {
                    case "-private":
                        sender.sendMessage(ChatColor.GOLD + "Profile set to private.");
                        return true;
                    case "-public":
                        sender.sendMessage(ChatColor.GOLD + "Profile set to public.");
                        return true;
                    default:
                        sender.sendMessage(ChatColor.RED + "Unknown option.");
                        return true;
                }
            } else {
                String target = args[0];




            }

        }

        return true;
    }

}
