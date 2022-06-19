package ru.itmo.server.collection.dao;

import ru.itmo.common.general.User;
import ru.itmo.common.model.Car;
import ru.itmo.common.model.Coordinates;
import ru.itmo.common.model.HumanBeing;
import ru.itmo.common.model.Mood;
import ru.itmo.server.general.ServerLoader;

import java.lang.reflect.Type;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class PosgreSqlDAO implements DAO{

    private User user;

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int add(Object obj) {
        int id = 0;
        HumanBeing humanBeing = (HumanBeing) obj;
        try {
            String sql = "INSERT INTO human_being_collection (creationDate, name, soundtrackName, minutesOfWaiting," +
                    " impactSpeed, realHero, hasToothpick, coordinates_x, coordinates_y, mood, car_name," +
                    "car_cool,  login) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING id";
            PreparedStatement stmt = ServerLoader.getConnection().prepareStatement(sql);
            stmt.setDate(1, java.sql.Date.valueOf(humanBeing.getCreationDate()));
            stmt.setString(2, humanBeing.getName());
            stmt.setString(3, humanBeing.getSoundtrackName());
            stmt.setLong(4, humanBeing.getMinutesOfWaiting());
            stmt.setInt(5, humanBeing.getImpactSpeed());
            stmt.setBoolean(6, humanBeing.isRealHero());

            if(humanBeing.isHasToothpick() == null) {
                stmt.setNull(7, Types.BOOLEAN);
            } else {
                stmt.setBoolean(7, humanBeing.isHasToothpick());
            }

            stmt.setInt(8, humanBeing.getCoordinates().getX());
            stmt.setFloat(9, humanBeing.getCoordinates().getY());

            if(humanBeing.getMood() == null) {
                stmt.setNull(10, Types.CHAR);
            } else {
                stmt.setString(10, humanBeing.getMood().name());
            }


            if(humanBeing.getMood() == null) {
                stmt.setNull(11, Types.CHAR);
            } else {
                stmt.setString(11, humanBeing.getCar().getCarName());
            }

            stmt.setBoolean(12, humanBeing.getCar().getCarCool());
            stmt.setString(13, user.getUsername());
            stmt.execute();

            ResultSet resultSet = stmt.getResultSet();
            resultSet.next();
            id = resultSet.getInt("id");
            return id;
        } catch(SQLException e) {
            return -1;
        }
    }

    @Override
    public boolean update(Object obj) {
        HumanBeing humanBeing = (HumanBeing) obj;
        String sql = null;
        sql = "UPDATE human_being_collection SET " +
                "name = ?," +
                "soundtrackName = ?," +
                "minutesOfWaiting = ?," +
                "impactSpeed = ?," +
                "realHero = ?," +
                "hasToothpick = ?, " +
                "coordinates_x = ?, " +
                "coordinates_y = ?, " +
                "mood = ?, " +
                "car_name = ?, " +
                "car_cool = ? " +
                "WHERE " +
                "id = " + humanBeing.getId() + " AND login = '" + user.getUsername() + "'";
        try {
            PreparedStatement stmt = ServerLoader.getConnection().prepareStatement(sql);

            stmt.setString(1, humanBeing.getName());
            stmt.setString(2, humanBeing.getSoundtrackName());
            stmt.setLong(3, humanBeing.getMinutesOfWaiting());
            stmt.setInt(4, humanBeing.getImpactSpeed());
            stmt.setBoolean(5, humanBeing.isRealHero());

            if(humanBeing.isHasToothpick() == null) {
                stmt.setNull(6, Types.BOOLEAN);
            } else {
                stmt.setBoolean(6, humanBeing.isHasToothpick());
            }

            stmt.setInt(7, humanBeing.getCoordinates().getX());
            stmt.setFloat(8, humanBeing.getCoordinates().getY());

            if(humanBeing.getMood() == null) {
                stmt.setNull(9, Types.CHAR);
            } else {
                stmt.setString(9, humanBeing.getMood().name());
            }

            stmt.setString(10, humanBeing.getCar().getCarName());
            stmt.setBoolean(11, humanBeing.getCar().getCarCool());

            stmt.execute();

            return true;
        } catch(SQLException e) {
            return false;
        }
    }

    @Override
    public boolean delete(int index) {
        try {
            String sql = "DELETE FROM human_being_collection WHERE " +
                    "id = " + index + " AND login = '" + user.getUsername() + "'";
            PreparedStatement stmt = ServerLoader.getConnection().prepareStatement(sql);
            stmt.execute();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public HumanBeing get(Object obj) {
        String sql = "SELECT * FROM human_being_collection WHERE id = " + obj;

        HumanBeing human = null;
        try {
            PreparedStatement stmt = ServerLoader.getConnection().prepareStatement(sql);
            stmt.execute();
            ResultSet result = stmt.getResultSet();
            while (result.next()) {
                human = new HumanBeing(
                        result.getString("name"),
                        result.getString("soundtrackName"),
                        result.getLong("minutesOfWaiting"),
                        result.getInt("impactSpeed"),
                        result.getBoolean("realHero"),
                        result.getBoolean("hasToothpick"),
                        new Coordinates(result.getInt("coordinates_x"), result.getFloat("coordinates_y")),
                        getMood(result.getString("mood")),
                        new Car(result.getString("car_name"), result.getBoolean("car_cool")));
                human.setId(result.getInt("id"));
                human.setCreationDate(result.getDate("creationDate").toLocalDate());
                User user = new UserDAO().getUser(result.getString("login"));
                human.setUser(user);
            }
        } catch (SQLException e) {
            System.out.println("Случилась еще одна хуета");
        }
        return human;
    }

    private Mood getMood(String obj) {
        if(obj == null) return null;
        return Mood.valueOf(obj);
    }

    public String getAll() {
        StringBuilder result = new StringBuilder();
        try {
            String sql = "SELECT id FROM human_being_collection ORDER BY id";
            PreparedStatement stmt = ServerLoader.getConnection().prepareStatement(sql);
            stmt.execute();
            ResultSet resultSet = stmt.getResultSet();

            while(resultSet.next()) {
                result.append(resultSet.getInt("id")).append(" ");
            }
        } catch (SQLException e) {
            return null;
        }
        return result.toString();
    }
}
