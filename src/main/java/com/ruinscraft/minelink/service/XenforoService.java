package com.ruinscraft.minelink.service;

import com.ruinscraft.minelink.LinkUser;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/*
 *  https://xenforo.com/community/pages/api-endpoints
 *
 *  Update a user
 *  POST    http://board.com/api/users/{id}/
 *  user_group_id           integer
 *  secondary_group_ids     integer[]
 */
public class XenforoService extends Service {

    private final String boardUrl;
    private final String boardApiUrl;
    private final String apiKey;

    public XenforoService(String boardUrl, String apiKey, List<GroupMapping> groupMappings) {
        super("xenforo", groupMappings);

        if (boardUrl.endsWith("/")) {
            throw new RuntimeException("Board URL cannot end with a trailing /");
        }

        this.boardUrl = boardUrl;
        this.boardApiUrl = boardUrl + "/api";
        this.apiKey = apiKey;
    }

    @Override
    protected void link(Player player, LinkUser linkUser, String code) {
        // PUT  http://board.com/api/minelink/codes/<code>
        // Headers: [XF-Api-Key => <XfKey>]
        CompletableFuture.runAsync(() -> {
            try {
                URL url = new URL(boardApiUrl + "/minelink/codes/" + code);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("PUT");
                connection.setRequestProperty("XF-Api-Key", apiKey);
                connection.getResponseCode();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).thenRun(() -> {
            if (player.isOnline()) {
                player.sendMessage(ChatColor.GOLD + "Please visit this URL to link your account:");
                player.sendMessage(ChatColor.GOLD + boardUrl + "/link?code=" + code);
            }
        });
    }

    @Override
    protected void setGroups(String serviceUserId, List<String> serviceGroupIds) {
        CompletableFuture.runAsync(() -> {
            try {
                URL url = new URL(boardApiUrl + "/users/" + serviceUserId + "?secondary_group_ids=[" + String.join(",", serviceGroupIds) + "]");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                connection.setRequestProperty("XF-Api-Key", apiKey);
                connection.getResponseCode();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

}
