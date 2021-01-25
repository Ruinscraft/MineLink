package com.ruinscraft.minelink.service;

import com.ruinscraft.minelink.LinkedAccount;
import com.ruinscraft.minelink.MineLinkPlugin;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.NodeType;
import net.luckperms.api.node.types.InheritanceNode;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

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

    public void setGroups(MineLinkPlugin mineLinkPlugin, Player player) {
        User user = mineLinkPlugin.getLuckPerms().getPlayerAdapter(Player.class).getUser(player);
        Set<String> groups = user.getNodes().stream()
                .filter(NodeType.INHERITANCE::matches)
                .map(NodeType.INHERITANCE::cast)
                .map(InheritanceNode::getGroupName)
                .collect(Collectors.toSet());

        mineLinkPlugin.getMineLinkStorage().queryLinkedAccounts(player.getUniqueId()).thenAccept(linkedAccounts -> {
            for (LinkedAccount linkedAccount : linkedAccounts) {
                if (linkedAccount.getServiceAccountId() == null) {
                    continue;
                }

                Service service = linkedAccount.getService(mineLinkPlugin);

                if (service != null) {
                    setGroups0(linkedAccount.getServiceAccountId(), groups);
                }
            }
        });
    }

    protected abstract void setGroups0(String serviceAccountId, Set<String> groups);

    public abstract boolean isPending(UUID mojangId);

    public static String generateCode() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}
