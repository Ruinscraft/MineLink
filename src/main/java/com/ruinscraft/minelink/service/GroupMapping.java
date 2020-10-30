package com.ruinscraft.minelink.service;

public class GroupMapping {

    private final String minecraftGroup;
    private final String serviceGroup;

    public GroupMapping(String minecraftGroup, String serviceGroup) {
        this.minecraftGroup = minecraftGroup;
        this.serviceGroup = serviceGroup;
    }

    public String getMinecraftGroup() {
        return minecraftGroup;
    }

    public String getServiceGroup() {
        return serviceGroup;
    }

}
