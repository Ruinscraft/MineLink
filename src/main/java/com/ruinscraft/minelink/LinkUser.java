package com.ruinscraft.minelink;

import java.util.UUID;

public class LinkUser {

    private int id;
    private UUID mojangId;
    private String minecraftUsername;
    private boolean isPrivate;

    public LinkUser(int id, UUID mojangId, String minecraftUsername) {
        this.id = id;
        this.mojangId = mojangId;
        this.minecraftUsername = minecraftUsername;
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

}
