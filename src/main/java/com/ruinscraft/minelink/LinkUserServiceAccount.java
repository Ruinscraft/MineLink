package com.ruinscraft.minelink;

public class LinkUserServiceAccount<T> {

    private final String serviceName;
    private final T id;

    public LinkUserServiceAccount(String serviceName, T id) {
        this.serviceName = serviceName;
        this.id = id;
    }

    public String getServiceName() {
        return serviceName;
    }

    public T getId() {
        return id;
    }

}
