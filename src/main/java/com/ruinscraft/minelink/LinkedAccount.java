package com.ruinscraft.minelink;

import com.ruinscraft.minelink.service.Service;

import java.util.UUID;

public class LinkedAccount {

    private UUID mojangId;
    private String serviceName;
    private String serviceAccountId;
    private long linkedAt;

    public LinkedAccount(UUID mojangId, String serviceName, String serviceAccountId, long linkedAt) {
        this.mojangId = mojangId;
        this.serviceName = serviceName;
        this.serviceAccountId = serviceAccountId;
        this.linkedAt = linkedAt;
    }

    public UUID getMojangId() {
        return mojangId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getServiceAccountId() {
        return serviceAccountId;
    }

    public long getLinkedAt() {
        return linkedAt;
    }

    public Service getService(MineLinkPlugin mineLinkPlugin) {
        return mineLinkPlugin.getServiceManager().getService(serviceName);
    }

}
