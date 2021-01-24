package com.ruinscraft.minelink.storage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLMineLinkStorage extends SQLMineLinkStorage {

    private String host;
    private int port;
    private String database;
    private String username;
    private String password;

    public MySQLMineLinkStorage(String host, int port, String database, String username, String password) {
        this.host = host;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;

        createTables();
    }

    @Override
    public Connection createConnection() throws SQLException {
        String jdbcUrl = "jdbc:mysql://" + host + ":" + port + "/" + database;
        return DriverManager.getConnection(jdbcUrl, username, password);
    }

}
