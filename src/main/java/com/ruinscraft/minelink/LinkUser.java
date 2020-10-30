package com.ruinscraft.minelink;

import com.ruinscraft.minelink.service.ServiceAccount;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LinkUser {

    private int id;
    private UUID mojangId;
    private String minecraftUsername;
    private boolean isPrivate;
    private Map<String, ServiceAccount> serviceAccounts;

    public LinkUser(int id, UUID mojangId, String minecraftUsername, boolean isPrivate) {
        this.id = id;
        this.mojangId = mojangId;
        this.minecraftUsername = minecraftUsername;
        this.isPrivate = isPrivate;
        serviceAccounts = new HashMap<>();
    }

    public int getId() {
        return id;
    }

    public UUID getMojangId() {
        return mojangId;
    }

    public String getMinecraftUsername() {
        return minecraftUsername;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setIsPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public <T> void addServiceAccount(ServiceAccount<T> account) {
        serviceAccounts.put(account.getServiceName(), account);
    }

    public <T> ServiceAccount<T> getServiceAccount(String serviceName) {
        return serviceAccounts.get(serviceName);
    }

}
