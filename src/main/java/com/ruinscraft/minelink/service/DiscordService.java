package com.ruinscraft.minelink.service;

import org.bukkit.entity.Player;

import java.util.List;
import java.util.Set;

public class DiscordService extends Service {
    // TODO: use JDA or Discord4J

    public DiscordService(List<GroupMapping> groupMappings) {
        super("discord", groupMappings);
    }

    @Override
    protected void link(Player player, String code) {

    }

    @Override
    public void setGroups(String serviceAccountId, Set<String> groups) {

    }

}
