package com.ruinscraft.minelink.service;

import com.ruinscraft.minelink.LinkUser;
import com.ruinscraft.minelink.MineLinkPlugin;
import org.bukkit.entity.Player;

import java.util.Set;

public abstract class Service<GROUPIDTYPE> {

    private final String name;
    private final Set<GroupMapping<GROUPIDTYPE>> groupMappings;

    public Service(String name, Set<GroupMapping<GROUPIDTYPE>> groupMappings) {
        this.name = name;
        this.groupMappings = groupMappings;
    }

    public String getName() {
        return name;
    }

    public Set<GroupMapping<GROUPIDTYPE>> getGroupMappings() {
        return groupMappings;
    }

    public GROUPIDTYPE getServiceGroup(String minecraftGroup) {
        for (GroupMapping<GROUPIDTYPE> mapping : groupMappings) {
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

        link(player, linkUser);

        return LinkResult.OK;
    }

    protected abstract void link(Player player, LinkUser linkUser);

    public abstract void addGroup(Player player, String groupName);

    public abstract void removeGroup(Player player, String groupName);

}
