package ru.itmo.common.model;

public enum Mood {
    SADNESS,
    LONGING,
    GLOOM,
    RAGE;

    public String toString() {
        return this.name();
    }
}