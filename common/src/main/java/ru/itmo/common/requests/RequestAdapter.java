package ru.itmo.common.requests;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import ru.itmo.common.general.CommandType;
import ru.itmo.common.general.User;
import ru.itmo.common.model.Car;
import ru.itmo.common.model.Coordinates;
import ru.itmo.common.model.HumanBeing;
import ru.itmo.common.model.Mood;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class RequestAdapter extends TypeAdapter<Request> {
    @Override
    public void write(JsonWriter out, Request value) throws IOException {

    }

    @Override
    public Request read(JsonReader in) throws IOException {
        in.beginObject();
        String fieldName = null;
        CommandType command = null;
        Object arg = null;
        String colour;
        User user = null;

        while(in.hasNext()) {
            JsonToken token = in.peek();

            if(token.equals(JsonToken.NAME)) {
                fieldName = in.nextName();
            }

            if("command".equals(fieldName)) {
                token = in.peek();
                command = CommandType.valueOf(in.nextString());
            }

            if("arguments".equals(fieldName)) {
                token = in.peek();
                arg = readHuman(in);
            }

            if("user".equals(fieldName)) {
                token = in.peek();
                user = readUser(in);
            }

            if("colour".equals(fieldName)) {
                token = in.peek();
                arg = in.nextString();
            }
        }
        in.endObject();
        return new Request(command, null, user);
    }

    public User readUser(JsonReader in) throws IOException {
        in.beginObject();
        String fieldName = null;
        String username = null;
        String password = null;

        while(in.hasNext()) {
            JsonToken token = in.peek();

            if(token.equals(JsonToken.NAME)) {
                fieldName = in.nextName();
            }

            if("username".equals(fieldName)) {
                token = in.peek();
                username = in.nextString();
            }

            if("password".equals(fieldName)) {
                token = in.peek();
                password = in.nextString();
            }
        }
        in.endObject();
        return new User(username, password);
    }

    public HumanBeing readHuman(JsonReader in) throws IOException {
        in.beginObject();
        String fieldName = null;
        HumanBeing human = new HumanBeing();

        while(in.hasNext()) {
            JsonToken token = in.peek();

            if(token.equals(JsonToken.NAME)) {
                fieldName = in.nextName();
            }

            if("id".equals(fieldName)) {
                token = in.peek();
                human.setId(in.nextInt());
            }

            if("name".equals(fieldName)) {
                token = in.peek();
                human.setName(in.nextString());
            }

            if("soundtrackName".equals(fieldName)) {
                token = in.peek();
                human.setSoundtrackName(in.nextString());
            }

            if("minutesOfWaiting".equals(fieldName)) {
                token = in.peek();
                human.setMinutesOfWaiting(in.nextLong());
            }

            if("impactSpeed".equals(fieldName)) {
                token = in.peek();
                human.setImpactSpeed(in.nextInt());
            }

            if("realHero".equals(fieldName)) {
                token = in.peek();
                human.setRealHero(in.nextBoolean());
            }

            if("hasToothpick".equals(fieldName)) {
                token = in.peek();
                human.setHasToothpick(in.nextBoolean());
            }

            if("coordinates".equals(fieldName)) {
                token = in.peek();
                human.setCoordinates(readCoordinates(in));
            }

            if("mood".equals(fieldName)) {
                token = in.peek();
                human.setMood(Mood.valueOf(in.nextString()));
            }

            if("car".equals(fieldName)) {
                token = in.peek();
                human.setCar(readCar(in));
            }
        }
        in.endObject();
        return human;
    }

    public Car readCar(JsonReader in) throws IOException {
        in.beginObject();
        String fieldName = null;
        Car car = new Car();

        while(in.hasNext()) {
            JsonToken token = in.peek();

            if(token.equals(JsonToken.NAME)) {
                fieldName = in.nextName();
            }

            if("name".equals(fieldName)) {
                token = in.peek();
                car.setCarName(in.nextString());
            }

            if("cool".equals(fieldName)) {
                token = in.peek();
                car.setCarCool(in.nextBoolean());
            }
        }
        in.endObject();
        return car;
    }

    public Coordinates readCoordinates(JsonReader in) throws IOException {
        in.beginObject();
        String fieldName = null;
        Coordinates coo = new Coordinates();

        while(in.hasNext()) {
            JsonToken token = in.peek();

            if(token.equals(JsonToken.NAME)) {
                fieldName = in.nextName();
            }

            if("x".equals(fieldName)) {
                token = in.peek();
                coo.setX(in.nextInt());
            }

            if("y".equals(fieldName)) {
                token = in.peek();
                coo.setY((float) in.nextDouble());
            }
        }
        in.endObject();
        return coo;
    }
}
