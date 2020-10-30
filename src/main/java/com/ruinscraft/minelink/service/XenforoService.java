package com.ruinscraft.minelink.service;

import com.ruinscraft.minelink.LinkUser;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Set;

/*
 *  https://xenforo.com/community/pages/api-endpoints
 *
 *
 *  Update a user
 *  POST    http://board.com/api/users/{id}/
 *  user_group_id           integer
 *  secondary_group_ids     integer[]
 *
 */
public class XenforoService extends Service<Integer> {

    private final String boardApiUrl;
    private final String apiKey;

    public XenforoService(String boardUrl, String apiKey, Set<GroupMapping<Integer>> groupMappings) {
        super("xenforo", groupMappings);

        if (boardUrl.endsWith("/")) {
            throw new RuntimeException("Board URL cannot end with a trailing /");
        }

        this.boardApiUrl = boardUrl + "/api";
        this.apiKey = apiKey;
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
