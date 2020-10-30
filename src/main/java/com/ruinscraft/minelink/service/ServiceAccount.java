package com.ruinscraft.minelink.service;

public class ServiceAccount {

    private String serviceName;
    private String accountId;
    private long linkedAt;

    public ServiceAccount(String serviceName, String accountId, long linkedAt) {
        this.serviceName = serviceName;
        this.accountId = accountId;
        this.linkedAt = linkedAt
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getAccountId() {
        return accountId;
    }

    public long getLinkedAt() {
        return linkedAt;
    }

}
