package com.example.privalov;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class DatabaseServiceImpl implements DatabaseService {

    private static final String DB_URL = "jdbc:postgresql://services.tms-studio.ru:8095/service_db";
    private Connection conn;

    public DatabaseServiceImpl() throws SQLException {
        conn = DriverManager.getConnection(DB_URL, "service_admin", "srv0983_");
        System.out.println("Connection is " + (conn.isValid(0) ? "up" : "down"));
    }

    @Override
    public Valute getValuteOfDate(LocalDate date) throws SQLException {
        PreparedStatement select = conn.prepareStatement(
                "SELECT * FROM val_curs WHERE date=?"
        );
        select.setDate(1, Date.valueOf(date));
        ResultSet resultSet = select.executeQuery();
        if (resultSet.next()) {
            return new Valute(null, resultSet.getString("valute_name"), resultSet.getDouble("value"));
        }
        return null;
    }

    @Override
    public void saveMaxValuteOfDate(String fio, Valute valute, LocalDate date) throws SQLException {
        PreparedStatement insert = conn.prepareStatement(
                "INSERT INTO val_curs (fio, valute_name, value, date) VALUES (?, ?, ?, ?)"
        );
        insert.setString(1, fio);
        insert.setString(2, valute.getName());
        insert.setDouble(3, valute.getValue());
        insert.setDate(4, Date.valueOf(date));
        insert.executeUpdate();
    }
}
