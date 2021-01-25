package com.ruinscraft.minelink;

import com.ruinscraft.minelink.service.Service;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.NodeType;
import net.luckperms.api.node.types.InheritanceNode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Set;
import java.util.stream.Collectors;

public class PlayerJoinListener implements Listener {

    private MineLinkPlugin mineLinkPlugin;

    public PlayerJoinListener(MineLinkPlugin mineLinkPlugin) {
        this.mineLinkPlugin = mineLinkPlugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        User user = mineLinkPlugin.getLuckPerms().getPlayerAdapter(Player.class).getUser(player);
        Set<String> groups = user.getNodes().stream()
                .filter(NodeType.INHERITANCE::matches)
                .map(NodeType.INHERITANCE::cast)
                .map(InheritanceNode::getGroupName)
                .collect(Collectors.toSet());

        mineLinkPlugin.getMineLinkStorage().queryLinkedAccounts(player.getUniqueId()).thenAccept(linkedAccounts -> {
            for (LinkedAccount linkedAccount : linkedAccounts) {
                if (linkedAccount.getServiceAccountId() == null) {
                    continue;
                }

                Service service = linkedAccount.getService(mineLinkPlugin);

                if (service != null) {
                    service.setGroups(linkedAccount.getServiceAccountId(), groups);
                }
            }
        });
    }

}
