package com.ruinscraft.minelink.service;

public class ServiceAccount<T> {

    private String serviceName;
    private T account;

    public ServiceAccount(String serviceName, T account) {
        this.serviceName = serviceName;
        this.account = account;
    }

    public String getServiceName() {
        return serviceName;
    }

    public T getAccount() {
        return account;
    }

}
