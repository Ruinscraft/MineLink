package com.ruinscraft.minelink.service;

import com.ruinscraft.minelink.LinkedAccount;
import com.ruinscraft.minelink.MineLinkPlugin;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public abstract class Service {

    private final String name;
    private final List<GroupMapping> groupMappings;

    public Service(String name, List<GroupMapping> groupMappings) {
        this.name = name;
        this.groupMappings = groupMappings;
    }

    public String getName() {
        return name;
    }

    public List<GroupMapping> getGroupMappings() {
        return groupMappings;
    }

    public String getServiceGroup(String minecraftGroup) {
        for (GroupMapping mapping : groupMappings) {
            if (mapping.getMinecraftGroup().equals(minecraftGroup)) {
                return mapping.getServiceGroup();
            }
        }

        return null;
    }

    public CompletableFuture<LinkResult> link(Player player, MineLinkPlugin mineLink) {
        return CompletableFuture.supplyAsync(() -> {
            List<LinkedAccount> linkedAccounts = mineLink.getMineLinkStorage().queryLinkedAccounts(player.getUniqueId()).join();

            for (LinkedAccount linkedAccount : linkedAccounts) {
                if (linkedAccount.getServiceName().equals(getName())) {
                    if (player.isOnline()) {
                        player.sendMessage(ChatColor.RED + "You already have linked your account to " + getName() + ".");
                    }

                    return LinkResult.ALREADY_LINKED;
                }
            }

            String code = generateCode();

            link(player, code);

            return LinkResult.OK;
        });
    }

    protected abstract void link(Player player, String code);

    // Run on player join
    public abstract void setGroups(String serviceAccountId, Set<String> groups);

    public static String generateCode() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}
