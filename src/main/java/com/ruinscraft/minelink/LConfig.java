package com.ruinscraft.minelink;

import com.ruinscraft.minelink.service.GroupMapping;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LConfig {

    private JavaPlugin plugin;

    public LConfig(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public String storageType = "mysql";
    public String storageMysqlHost = "localhost";
    public int storageMysqlPort = 3306;
    public String storageMysqlDatabase = "minelink";
    public String storageMysqlUsername = "root";
    public String storageMysqlPassword = "password";
    public List<String> serviceUse = Arrays.asList("xenforo", "discord");
    public String serviceXenforoBoardUrl = "myxenforoboard.com";
    public String serviceXenforoApiKey = "abc123";
    public String serviceDiscordToken = "abc123";

    public <T> Set<GroupMapping<T>> getGroupMappings(String serviceName) {
        Set<GroupMapping<T>> groupMappings = new HashSet<>();
        ConfigurationSection groupMappingsSection = plugin.getConfig().getConfigurationSection("services." + serviceName + ".group-mappings");

        for (String minecraftGroup : groupMappingsSection.getKeys(false)) {
            Object serviceGroup = groupMappingsSection.get(minecraftGroup);
            GroupMapping groupMapping = new GroupMapping(minecraftGroup, serviceGroup);
            groupMappings.add(groupMapping);
        }

        return groupMappings;
    }

}
