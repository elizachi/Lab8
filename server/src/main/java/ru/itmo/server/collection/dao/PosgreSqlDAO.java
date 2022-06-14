package ru.itmo.server.collection.dao;

import ru.itmo.common.general.User;
import ru.itmo.common.model.HumanBeing;

public class PosgreSqlDAO implements DAO{

    private User user;

    @Override
    public int add(Object obj) {
        return 0;
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
