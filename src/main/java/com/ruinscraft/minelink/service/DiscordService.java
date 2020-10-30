package com.ruinscraft.minelink.service;

import com.ruinscraft.minelink.LinkUser;
import org.bukkit.entity.Player;

import java.util.Set;

public class DiscordService extends Service<Integer> {

    // TODO: use JDA or Discord4J

    public DiscordService(Set<GroupMapping<Integer>> groupMappings) {
        super("discord", groupMappings);
    }

    @Override
    protected void link(Player player, LinkUser linkUser) {

    }

    @Override
    public void addGroup(Player player, String groupName) {

    }

    @Override
    public void removeGroup(Player player, String groupName) {

    }

}
