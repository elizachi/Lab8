package ru.itmo.server.collection.dao;

import ru.itmo.common.general.User;
import ru.itmo.server.general.Loader;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO implements DAO {
    @Override
    public int add(Object obj) {
        User user = (User) obj;
        String sql = "INSERT INTO USERS (login, password) VALUES (?, ?)";
        try {
            PreparedStatement stmt = Loader.getConnection().prepareStatement(sql);
            stmt.setString(1, user.getUsername());
            stmt.setString(2, User.getHash(user.getPassword()));
            stmt.execute();
            return 1;
        } catch(SQLException e) {
            return -1;
        }
    }

    @Override
    public boolean update(Object obj) {
        return false;
    }

    @Override
    public boolean delete(int index) {
        return false;
    }

    @Override
    public User get(Object obj) {
        User user = (User) obj;
        String sql = "SELECT login, password FROM users WHERE login = '" +user.getUsername()+ "' AND " +
                " password = '" +User.getHash(user.getPassword()) + "'";
        try {
            PreparedStatement stmt = Loader.getConnection().prepareStatement(sql);
            ResultSet result = stmt.executeQuery();

            result.next();
            String login = result.getString("login");
            String password = result.getString("password");
            return user;
        } catch (SQLException e) {
            return null;
        }
    }
}
