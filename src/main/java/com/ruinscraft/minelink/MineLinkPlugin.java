package com.ruinscraft.minelink;

import com.ruinscraft.minelink.service.*;
import com.ruinscraft.minelink.storage.MineLinkStorage;
import com.ruinscraft.minelink.storage.MySQLMineLinkStorage;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class MineLinkPlugin extends JavaPlugin {

    private LConfig lConfig;
    private MineLinkStorage mineLinkStorage;
    private ServiceManager serviceManager;

    public LConfig getlConfig() {
        return lConfig;
    }

    public MineLinkStorage getMineLinkStorage() {
        return mineLinkStorage;
    }

    public ServiceManager getServiceManager() {
        return serviceManager;
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();
        lConfig = new LConfig(this);

        serviceManager = new ServiceManager();

        for (String serviceName : lConfig.serviceUse) {
            switch (serviceName.toLowerCase()) {
                case "xenforo":
                    String xfUrl = lConfig.serviceXenforoBoardUrl;
                    String xfKey = lConfig.serviceXenforoApiKey;
                    List<GroupMapping> xfMappings = lConfig.getGroupMappings("xenforo");
                    serviceManager.addService(new XenforoService(xfUrl, xfKey, xfMappings));
                    break;
                case "discord":
                    List<GroupMapping> discordMappings = lConfig.getGroupMappings("discord");
                    serviceManager.addService(new DiscordService(discordMappings));
                    break;
                default:
                    getLogger().warning("Unknown service: " + serviceName);
                    break;
            }
        }

        for (Service service : serviceManager.getServices()) {
            getLogger().info("Using service: " + service.getName());
        }

        if (lConfig.storageType.equals("mysql")) {
            String host = lConfig.storageMysqlHost;
            int port = lConfig.storageMysqlPort;
            String db = lConfig.storageMysqlDatabase;
            String user = lConfig.storageMysqlUsername;
            String pass = lConfig.storageMysqlPassword;

            mineLinkStorage = new MySQLMineLinkStorage(host, port, db, user, pass);
        } else {
            throw new RuntimeException("No valid storage type defined");
        }

        getCommand("link").setExecutor(new LinkCommandExecutor(this));
    }

}
