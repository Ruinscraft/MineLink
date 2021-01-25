package com.ruinscraft.minelink.service;

import org.bukkit.entity.Player;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public class DiscordService extends Service {
    // TODO: use JDA or Discord4J

    public DiscordService(List<GroupMapping> groupMappings) {
        super("discord", groupMappings);
    }

    @Override
    protected void link(Player player, String code) {

    }

    @Override
    protected void setGroups0(String serviceAccountId, Set<String> groups) {

    }

    @Override
    public boolean isPending(UUID mojangId) {
        return false;
    }

}
