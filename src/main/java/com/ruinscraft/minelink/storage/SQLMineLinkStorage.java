package com.ruinscraft.minelink.storage;

import com.ruinscraft.minelink.LinkedAccount;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public abstract class SQLMineLinkStorage implements MineLinkStorage {

    protected void createTables() {
        try (Connection connection = createConnection()) {
            try (Statement statement = connection.createStatement()) {
                statement.addBatch("CREATE TABLE IF NOT EXISTS linked_accounts (mojang_id VARCHAR(36) NOT NULL, service_name VARCHAR(32), service_account_id VARCHAR(64), linked_at BIGINT, UNIQUE KEY linked_account (mojang_id, service_name));");
                statement.executeBatch();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public CompletableFuture<Void> insertLinkedAccount(LinkedAccount linkedAccount) {
        return CompletableFuture.runAsync(() -> {
            try (Connection connection = createConnection()) {
                try (PreparedStatement insert = connection.prepareStatement("INSERT INTO linked_accounts (mojang_id, service_name, service_account_id, linked_at) VALUES (?, ?, ?, ?);")) {
                    insert.setString(1, linkedAccount.getMojangId().toString());
                    insert.setString(2, linkedAccount.getServiceName());
                    insert.setString(3, linkedAccount.getServiceAccountId());
                    insert.setLong(4, linkedAccount.getLinkedAt());
                    insert.execute();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public CompletableFuture<List<LinkedAccount>> queryLinkedAccounts(UUID mojangId) {
        return CompletableFuture.supplyAsync(() -> {
            List<LinkedAccount> linkedAccounts = new ArrayList<>();

            try (Connection connection = createConnection()) {
                try (PreparedStatement query = connection.prepareStatement("SELECT * FROM linked_accounts WHERE mojang_id = ?;")) {
                    query.setString(1, mojangId.toString());

                    try (ResultSet resultSet = query.executeQuery()) {
                        while (resultSet.next()) {
                            String serviceName = resultSet.getString("service_name");
                            String serviceAccountId = resultSet.getString("service_account_id");
                            long linkedAt = resultSet.getLong("linked_at");
                            LinkedAccount linkedAccount = new LinkedAccount(mojangId, serviceName, serviceAccountId, linkedAt);
                            linkedAccounts.add(linkedAccount);
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return linkedAccounts;
        });
    }

    public abstract Connection createConnection() throws SQLException;

}
