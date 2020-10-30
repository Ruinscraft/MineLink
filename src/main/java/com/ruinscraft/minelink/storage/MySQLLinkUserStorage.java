package com.ruinscraft.minelink.storage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLLinkUserStorage extends SQLLinkUserStorage {

    private String host;
    private int port;
    private String database;
    private String username;
    private String password;

    public MySQLLinkUserStorage(String host, int port, String database, String username, String password) throws Exception {
        this.host = host;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;
    }

    @Override
    protected Connection getConnection() throws SQLException {
        String jdbcUrl = "jdbc:mysql://" + host + ":" + port + "/" + database;
        return DriverManager.getConnection(jdbcUrl, username, password);
    }

}
