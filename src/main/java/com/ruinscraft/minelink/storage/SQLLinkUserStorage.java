package com.ruinscraft.minelink.storage;

import com.ruinscraft.minelink.LinkUser;

import java.sql.*;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public abstract class SQLLinkUserStorage extends LinkUserStorage {

    /*
     *  link_users
     *      id                  INT
     *      mojang_uuid         VARCHAR(36)
     *      minecraft_username  VARCHAR(16)
     *      is_private          BOOL
     *
     *  link_user_accounts
     *      link_user_id        INT
     *      service_name        VARCHAR(32)
     *      service_account     VARCHAR(64)
     *      linked_at           BIGINT
     */


    public SQLLinkUserStorage() throws Exception {
        createTables();
    }

    @Override
    public CompletableFuture<Void> save(LinkUser linkUser) {
        return CompletableFuture.runAsync(() -> {
            try (Connection connection = getConnection()) {
                try (PreparedStatement upsert = connection.prepareStatement("")) {
                    // Get auto incremented primary key if inserted
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public CompletableFuture<LinkUser> load(UUID mojangId) {
        return CompletableFuture.supplyAsync(() -> {
            try (Connection connection = getConnection()) {
                try (PreparedStatement query = connection.prepareStatement("SELECT * FROM link_users WHERE mojang_uuid = ?;")) {
                    try (ResultSet result = query.executeQuery()) {

                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return null;
        });
    }

    @Override
    public CompletableFuture<LinkUser> load(String minecraftUsername) {
        return CompletableFuture.supplyAsync(() -> {
            try (Connection connection = getConnection()) {
                try (PreparedStatement query = connection.prepareStatement("SELECT * FROM link_users WHERE minecraft_username = ?;")) {
                    try (ResultSet result = query.executeQuery()) {

                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return null;
        });
    }

    protected void createTables() throws SQLException {
        try (Connection connection = getConnection()) {
            try (Statement statement = connection.createStatement()) {
                statement.execute("CREATE TABLE IF NOT EXISTS link_users (id INT NOT NULL AUTO_INCREMENT, mojang_uuid VARCHAR(36) NOT NULL, minecraft_username VARCHAR(16) NOT NULL, is_private BOOL, PRIMARY KEY (id), UNIQUE (mojang_uuid));");
                statement.execute("CREATE TABLE IF NOT EXISTS link_user_services (link_user_id INT NOT NULL, service_name VARCHAR(32) NOT NULL, service_account VARCHAR(64) NOT NULL, linked_at BIGINT, FOREIGN KEY (link_user_id) REFERENCES link_users (id))");
            }
        }
    }

    protected abstract Connection getConnection() throws SQLException;

}
