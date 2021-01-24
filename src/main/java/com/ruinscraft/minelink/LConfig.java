package com.ruinscraft.minelink;

import com.ruinscraft.minelink.service.GroupMapping;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class LConfig {

    private JavaPlugin plugin;

    public LConfig(JavaPlugin plugin) {
        this.plugin = plugin;

        storageType = plugin.getConfig().getString("storage.type");
        storageMysqlHost = plugin.getConfig().getString("storage.mysql.host");
        storageMysqlPort = plugin.getConfig().getInt("storage.mysql.port");
        storageMysqlDatabase = plugin.getConfig().getString("storage.mysql.database");
        storageMysqlUsername = plugin.getConfig().getString("storage.mysql.username");
        storageMysqlPassword = plugin.getConfig().getString("storage.mysql.password");
        serviceUse = plugin.getConfig().getStringList("services.use");
        serviceXenforoBoardUrl = plugin.getConfig().getString("services.xenforo.board-url");
        serviceXenforoApiKey = plugin.getConfig().getString("services.xenforo.api-key");

        // TODO: discord
    }

    public String storageType;
    public String storageMysqlHost;
    public int storageMysqlPort;
    public String storageMysqlDatabase;
    public String storageMysqlUsername;
    public String storageMysqlPassword;
    public List<String> serviceUse;
    public String serviceXenforoBoardUrl;
    public String serviceXenforoApiKey;
    public String serviceDiscordToken = "abc123";

    public List<GroupMapping> getGroupMappings(String serviceName) {
        List<GroupMapping> groupMappings = new ArrayList<>();
        ConfigurationSection groupMappingsSection = plugin.getConfig().getConfigurationSection("services." + serviceName + ".group-mappings");

        for (String minecraftGroup : groupMappingsSection.getKeys(false)) {
            String serviceGroup = groupMappingsSection.getString(minecraftGroup);
            GroupMapping groupMapping = new GroupMapping(minecraftGroup, serviceGroup);
            groupMappings.add(groupMapping);
        }

        return groupMappings;
    }

}
