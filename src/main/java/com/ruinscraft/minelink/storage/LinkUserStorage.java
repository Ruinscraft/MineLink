package com.ruinscraft.minelink.storage;

import com.ruinscraft.minelink.LinkUser;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

public abstract class LinkUserStorage {

    private Map<UUID, LinkUser> cache;

    public LinkUserStorage() {
        cache = new ConcurrentHashMap<>();
    }

    public LinkUser getLinkUser(Player player) {
        return getLinkUser(player.getUniqueId());
    }

    public LinkUser getLinkUser(UUID mojangId) {
        return cache.get(mojangId);
    }

    public CompletableFuture<LinkUser> getOrLoad(UUID mojangId) {
        {
            LinkUser user = getLinkUser(mojangId);

            if (user != null) {
                return CompletableFuture.completedFuture(user);
            }
        }

        CompletableFuture<LinkUser> future = load(mojangId);

        future.thenAccept(linkUser -> cache.put(mojangId, linkUser));

        return future;
    }

    public CompletableFuture<LinkUser> getOrLoad(Player player) {
        return getOrLoad(player.getUniqueId());
    }

    public boolean isLoaded(UUID mojangId) {
        return cache.containsKey(mojangId);
    }

    public void unload(UUID mojangId) {
        cache.remove(mojangId);
    }

    public void unload(Player player) {
        unload(player.getUniqueId());
    }

    public abstract CompletableFuture<Void> save(LinkUser linkUser);

    public abstract CompletableFuture<LinkUser> load(UUID mojangId);

    public abstract CompletableFuture<LinkUser> load(String minecraftUsername);

}
