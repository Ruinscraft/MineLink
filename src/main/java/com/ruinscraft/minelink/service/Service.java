package com.ruinscraft.minelink.service;

import com.ruinscraft.minelink.LinkUser;
import com.ruinscraft.minelink.MineLinkPlugin;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

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

    public LinkResult link(Player player, MineLinkPlugin mineLink) {
        LinkUser linkUser = mineLink.getLinkUserStorage().getLinkUser(player);

        if (linkUser.getServiceAccount(name) != null) {
            return LinkResult.ALREADY_LINKED;
        }

        String code = generateCode();

        link(player, linkUser, code);

        return LinkResult.OK;
    }

    protected abstract void link(Player player, LinkUser linkUser, String code);

    protected abstract void setGroups(String serviceAccountId, List<String> serviceGroupIds);

    public static String generateCode() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}
