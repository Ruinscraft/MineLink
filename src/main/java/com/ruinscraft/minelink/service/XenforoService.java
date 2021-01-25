package com.ruinscraft.minelink.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ruinscraft.minelink.LinkedAccount;
import com.ruinscraft.minelink.MineLinkPlugin;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

/*
 *  https://xenforo.com/community/pages/api-endpoints
 *
 *  Update a user
 *  POST    http://board.com/api/users/{id}/
 *  user_group_id           integer
 *  secondary_group_ids     integer[]
 *
 *  The plugin will check every 10s for 5m to see if
 *  the user has clicked the generated link
 *
 */
public class XenforoService extends Service {

    private static final JsonParser JSON_PARSER = new JsonParser();

    private final String boardUrl;
    private final String boardApiUrl;
    private final String apiKey;
    private Map<UUID, String> pendingCodes;

    public XenforoService(String boardUrl, String apiKey, List<GroupMapping> groupMappings, MineLinkPlugin mineLinkPlugin) {
        super("xenforo", groupMappings);

        if (boardUrl.endsWith("/")) {
            throw new RuntimeException("Board URL cannot end with a trailing /");
        }

        this.boardUrl = boardUrl;
        this.boardApiUrl = boardUrl + "/api";
        this.apiKey = apiKey;
        pendingCodes = new ConcurrentHashMap<>();

        mineLinkPlugin.getServer().getScheduler().runTaskTimerAsynchronously(mineLinkPlugin, () -> {
            for (UUID mojangId : pendingCodes.keySet()) {
                String code = pendingCodes.get(mojangId);
                JsonObject codeInfo = null;
                try {
                    codeInfo = getCodeInfo(code);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (codeInfo != null) {
                    // TODO: check if expired
                    int createdDate = codeInfo.get("code").getAsJsonObject().get("created_date").getAsInt();
                    int used = codeInfo.get("code").getAsJsonObject().get("used").getAsInt();
                    int userId = codeInfo.get("code").getAsJsonObject().get("user_id").getAsInt();

                    // Assume user_id is set if used
                    if (used == 1) {
                        LinkedAccount linkedAccount = new LinkedAccount(mojangId, getName(), Long.toString(userId), System.currentTimeMillis());
                        mineLinkPlugin.getMineLinkStorage().saveLinkedAccount(linkedAccount);
                        pendingCodes.remove(mojangId);
                    }
                }
            }
        }, 10 * 20L, 10 * 20L);
    }

    private JsonObject getCodeInfo(String code) throws Exception {
        URL url = new URL(boardApiUrl + "/minelink/codes/" + code);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("XF-Api-Key", apiKey);

        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            try (InputStreamReader isr = new InputStreamReader(connection.getInputStream())) {
                try (BufferedReader br = new BufferedReader(isr)) {
                    JsonElement json = JSON_PARSER.parse(br);

                    if (json instanceof JsonObject) {
                        return (JsonObject) json;
                    }
                }
            }
        }

        return null;
    }

    @Override
    protected void link(Player player, String code) {
        // PUT  https://board.com/api/minelink/codes/<code>
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
                pendingCodes.put(player.getUniqueId(), code);
            }
        });
    }

    @Override
    public void setGroups(String serviceUserId, Set<String> serviceGroupIds) {
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
