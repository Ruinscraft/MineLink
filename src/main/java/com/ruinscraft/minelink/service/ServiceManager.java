package com.ruinscraft.minelink.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ServiceManager {

    private Map<String, Service> services;

    public ServiceManager() {
        services = new HashMap<>();
    }

    public void addService(Service service) {
        services.put(service.getName(), service);
    }

    public Service getService(String name) {
        return services.get(name.toLowerCase());
    }

    public Collection<Service> getServices() {
        return services.values();
    }

}
