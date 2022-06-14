package ru.itmo.common.general;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;
import java.util.Objects;

/**
 * Класс для получения логина и пароля пользователя
 */
public class User {
    public String username;
    public String password;
    public String colour;

    public User(String username, String password, String colour) {
        this.username = username;
        this.password = password;
        this.colour = colour;
    }

    public User getEncodeUser() {
        this.username = encode(username);
        this.password = encode(password);
        this.colour = encode(colour);
        return this;
    }

    public User getDecodeUser() {
        this.username = decode(username);
        this.password = decode(password);
        this.colour = decode(colour);

        return this;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
    public String getColour() {
        return colour;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }
    /**
     * Метод для хеширования
     * @param word слово для хеширования
     * @return хешированное слово по стандарту SHA-224
     */
    public static String getHash(String word) {
        MessageDigest mD = null;
        try {
            mD = MessageDigest.getInstance("SHA-224");
        } catch (Exception e) {
            System.err.println("Проблемы с хешированием данных.");
        }
        assert mD != null;
        byte[] hash = mD.digest(word.getBytes(StandardCharsets.UTF_8));
        return Arrays.toString(hash);
    }

    private String encode(String data) {
        if(data != null) {
            return Base64.getEncoder().encodeToString(data.getBytes(StandardCharsets.UTF_8));
        } else {
            return null;
        }

    }

    private String decode(String data) {
        if(data != null) {
            return new String(Base64.getDecoder().decode(data.getBytes(StandardCharsets.UTF_8)));
        } else {
            return null;
        }
    }

    public User hash() {
        this.password = getHash(password);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(password, user.password) && Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(password, username);
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", colour='" + colour + '\'' +
                '}';
    }
}
