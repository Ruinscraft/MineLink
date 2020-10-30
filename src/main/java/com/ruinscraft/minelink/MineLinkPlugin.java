package com.ruinscraft.minelink;

import com.ruinscraft.minelink.command.LinkCommandExecutor;
import com.ruinscraft.minelink.command.LinkInfoCommandExecutor;
import com.ruinscraft.minelink.service.*;
import com.ruinscraft.minelink.storage.LinkUserStorage;
import com.ruinscraft.minelink.storage.MySQLLinkUserStorage;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Set;

public class MineLinkPlugin extends JavaPlugin {

    private LConfig lConfig;
    private LinkUserStorage linkUserStorage;
    private ServiceManager serviceManager;

    public LConfig getlConfig() {
        return lConfig;
    }

    public LinkUserStorage getLinkUserStorage() {
        return linkUserStorage;
    }

    public ServiceManager getServiceManager() {
        return serviceManager;
    }

    @Override
    public void onEnable() {
        // Load config
        saveDefaultConfig();
        lConfig = new LConfig(this);

        // Load services
        for (String serviceName : lConfig.serviceUse) {
            switch (serviceName.toLowerCase()) {
                case "xenforo":
                    String xfUrl = lConfig.serviceXenforoBoardUrl;
                    String xfKey = lConfig.serviceXenforoApiKey;
                    Set<GroupMapping<Integer>> xfMappings = lConfig.getGroupMappings("xenforo");
                    serviceManager.addService(new XenforoService(xfUrl, xfKey, xfMappings));
                    break;
                case "discord":
                    Set<GroupMapping<Integer>> dMappings = lConfig.getGroupMappings("discord");
                    serviceManager.addService(new DiscordService(dMappings));
                    break;
            }
        }

        for (Service service : serviceManager.getServices()) {
            getLogger().info("Using service: " + service.getName());
        }

        // Load storage
        if (lConfig.storageType.equals("mysql")) {
            String host = lConfig.storageMysqlHost;
            int port = lConfig.storageMysqlPort;
            String db = lConfig.storageMysqlDatabase;
            String user = lConfig.storageMysqlUsername;
            String pass = lConfig.storageMysqlPassword;

            try {
                linkUserStorage = new MySQLLinkUserStorage(host, port, db, user, pass);
            } catch (Exception e) {
                throw new RuntimeException("Could not initialize mysql storage", e);
            }
        } else {
            throw new RuntimeException("No valid storage type defined");
        }

        /* Register commands */
        getCommand("link").setExecutor(new LinkCommandExecutor(this));
        getCommand("linkinfo").setExecutor(new LinkInfoCommandExecutor(this));

        /* Register listeners */
        getServer().getPluginManager().registerEvents(new JoinQuitListener(this), this);


    }

    @Override
    public void onDisable() {

    }

}
