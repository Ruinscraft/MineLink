package com.ruinscraft.minelink.service;

public class GroupMapping<T> {

    private final String minecraftGroup;
    private final T serviceGroup;

    public GroupMapping(String minecraftGroup, T serviceGroup) {
        this.minecraftGroup = minecraftGroup;
        this.serviceGroup = serviceGroup;
    }

    public String getMinecraftGroup() {
        return minecraftGroup;
    }

    public T getServiceGroup() {
        return serviceGroup;
    }

}
