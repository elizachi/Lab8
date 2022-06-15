package ru.itmo.server.collection.dao;

import ru.itmo.common.general.User;
import ru.itmo.common.model.HumanBeing;
import ru.itmo.server.general.ServerLoader;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PosgreSqlDAO implements DAO{

    private User user;

    @Override
    public int add(Object obj) {
        int id = 0;
//        HumanBeing humanBeing = (HumanBeing) obj;
//        try {
//            String sql = "INSERT INTO human_being_collection (creationDate, name, soundtrackName, minutesOfWaiting," +
//                    " impactSpeed, realHero, hasToothpick, coordinates_x, coordinates_y, mood, car_name," +
//                    "car_cool,  login) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING id";
//            PreparedStatement stmt = ServerLoader.getConnection().prepareStatement(sql);
//            stmt.setDate(1, java.sql.Date.valueOf(humanBeing.getCreationDate()));
//            stmt.setString(2, humanBeing.getName());
//            stmt.setString(3, humanBeing.getSoundtrackName());
//            stmt.setLong(4, humanBeing.getMinutesOfWaiting());
//            stmt.setInt(5, humanBeing.getImpactSpeed());
//            stmt.setBoolean(6, humanBeing.isRealHero());
//            stmt.setBoolean(7, humanBeing.isHasToothpick());
//            stmt.setInt(8, humanBeing.getCoordinates().getX());
//            stmt.setFloat(9, humanBeing.getCoordinates().getY());
//            stmt.setString(10, convert(humanBeing.getMood()));
//            stmt.setString(11, humanBeing.getCar().getCarName());
//            stmt.setBoolean(12, humanBeing.getCar().getCarCool());
//            stmt.setString(13, user.getUsername());
//            stmt.execute();
//
//            ResultSet resultSet = stmt.getResultSet();
//            resultSet.next();
//            id = resultSet.getInt("id");
//            return id;
//        } catch(SQLException e) {
//            return -1;
//        }
        return id;
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
    public HumanBeing get(Object obj) {
        return null;
    }
}
