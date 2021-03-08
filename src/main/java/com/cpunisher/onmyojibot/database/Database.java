package com.cpunisher.onmyojibot.database;

import com.cpunisher.onmyojibot.OnmyojiBotConfig;
import com.cpunisher.onmyojibot.PluginMain;
import com.cpunisher.onmyojibot.database.model.DrawResult;
import com.cpunisher.onmyojibot.database.model.Player;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.io.IOException;
import java.sql.SQLException;

public class Database {
    public static final Database INSTANCE = new Database();

    private JdbcPooledConnectionSource connectionSource;
    private Dao<Player, Long> playerDao;
    private Dao<DrawResult, Integer> resultDao;

    public void connect() {
        OnmyojiBotConfig.DatabaseConfig databaseConfig = OnmyojiBotConfig.INSTANCE.getDatabase();
        String jdbcUrl = databaseConfig.getAddress();
        String username = databaseConfig.getUser();
        String password = databaseConfig.getPassword();

        try {
            connectionSource = new JdbcPooledConnectionSource(jdbcUrl, username, password);
            TableUtils.createTableIfNotExists(connectionSource, Player.class);
            TableUtils.createTableIfNotExists(connectionSource, DrawResult.class);
            playerDao = DaoManager.createDao(connectionSource, Player.class);
            resultDao = DaoManager.createDao(connectionSource, DrawResult.class);

            PluginMain.INSTANCE.getLogger().info("Database is connected!");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void close() {
        if (connectionSource != null) {
            try {
                connectionSource.close();
                PluginMain.INSTANCE.getLogger().info("Database is closed!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Dao<Player, Long> getPlayerDao() {
        return playerDao;
    }

    public Dao<DrawResult, Integer> getResultDao() {
        return resultDao;
    }
}
