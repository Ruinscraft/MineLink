package com.ruinscraft.minelink;

import com.ruinscraft.minelink.service.Service;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    private MineLinkPlugin mineLinkPlugin;

    public PlayerJoinListener(MineLinkPlugin mineLinkPlugin) {
        this.mineLinkPlugin = mineLinkPlugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        for (Service service : mineLinkPlugin.getServiceManager().getServices()) {
            service.setGroups(mineLinkPlugin, player);
        }
    }

}
