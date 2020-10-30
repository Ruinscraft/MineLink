package com.ruinscraft.minelink;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinQuitListener implements Listener {

    private MineLinkPlugin mineLink;

    public JoinQuitListener(MineLinkPlugin mineLink) {
        this.mineLink = mineLink;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        mineLink.getLinkUserStorage().getOrLoad(event.getPlayer());
    }

    public void onQuit(PlayerQuitEvent event) {
        mineLink.getLinkUserStorage().unload(event.getPlayer());
    }

}
