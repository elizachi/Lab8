package ru.itmo.server.collection.dao;

import ru.itmo.common.general.User;
import ru.itmo.server.general.ServerLoader;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import org.postgresql.util.PSQLException;

public class UserDAO implements DAO {
    @Override
    public int add(Object obj) {
        User user = (User) obj;
        String sql = "INSERT INTO users (login, password, colour) VALUES (?, ?, ?)";
        try {
            PreparedStatement stmt = ServerLoader.getConnection().prepareStatement(sql);
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getColour());
            stmt.execute();
            return 1;
        }catch (PSQLException e) {
            return 0;
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
        String sql = "SELECT password, colour FROM users WHERE login = '" +user.getUsername()+ "'";
        try {
            PreparedStatement stmt = ServerLoader.getConnection().prepareStatement(sql);
            ResultSet result = stmt.executeQuery();

            result.next();
            String password = result.getString("password");
            String color = result.getString("colour");
            user.setColour(color);

            if(!Objects.equals(password, User.getHash(user.getPassword()))) {
                user.setPassword(null);
                return user;
            }
            return user;
        } catch (SQLException e) {
            return null;
        }
    }

    public User getUser(String login) {
        String sql = "SELECT colour FROM users WHERE login = '" +login+ "'";
        User user = new User(login, null, null);
        try {
            PreparedStatement stmt = ServerLoader.getConnection().prepareStatement(sql);
            ResultSet result = stmt.executeQuery();

            result.next();
            String color = result.getString("colour");
            user.setColour(color);
            return user;
        } catch (SQLException e) {
            return null;
        }
    }
}
