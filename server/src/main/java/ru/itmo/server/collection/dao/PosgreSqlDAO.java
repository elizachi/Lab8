package ru.itmo.server.collection.dao;

import ru.itmo.common.general.User;
import ru.itmo.common.model.HumanBeing;

public class PosgreSqlDAO implements DAO{

    private User user;

    @Override
    public int add(HumanBeing humanBeing) {
        return 0;
    }

    @Override
    public boolean update(HumanBeing humanBeing) {
        return false;
    }

    @Override
    public boolean delete(int index) {
        return false;
    }

    @Override
    public HumanBeing get(int id) {
        return null;
    }
}
