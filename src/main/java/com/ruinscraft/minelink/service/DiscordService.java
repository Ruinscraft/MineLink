package com.ruinscraft.minelink.service;

import com.ruinscraft.minelink.LinkUser;
import org.bukkit.entity.Player;

import java.util.List;

public class DiscordService extends Service {
    // TODO: use JDA or Discord4J

    public DiscordService(List<GroupMapping> groupMappings) {
        super("discord", groupMappings);
    }

    @Override
    protected void link(Player player, LinkUser linkUser, String code) {

    }

    @Override
    protected void setGroups(String serviceAccountId, List<String> serviceGroupIds) {

    }

}
