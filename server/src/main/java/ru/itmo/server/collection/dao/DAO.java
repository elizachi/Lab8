package ru.itmo.server.collection.dao;

public interface DAO <T> {
    int add(T obj);
    boolean update(T obj);
    boolean delete(int obj);
    T get(T obj);
}
