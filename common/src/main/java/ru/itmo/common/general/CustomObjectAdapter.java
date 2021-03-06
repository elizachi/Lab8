package ru.itmo.common.general;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import ru.itmo.common.model.Car;
import ru.itmo.common.model.Coordinates;
import ru.itmo.common.model.HumanBeing;
import ru.itmo.common.model.Mood;

import java.io.IOException;
import java.time.LocalDate;

public class CustomObjectAdapter {

    public void writeHuman(JsonWriter out, HumanBeing human) throws IOException {
        if(human != null) {
            out.beginObject();
            out.name("id").value(human.getId());
            writeCreationDate(out, human);
            out.name("name").value(human.getName());
            out.name("soundtrackName").value(human.getSoundtrackName());
            out.name("minutesOfWaiting").value(human.getMinutesOfWaiting());
            out.name("impactSpeed").value(human.getImpactSpeed());
            out.name("realHero").value(human.isRealHero());
            out.name("hasToothpick").value(human.isHasToothpick());
            out.name("coordinates");
            writeCoordinates(out, human.getCoordinates());
            out.name("mood").value(valueEnum(human.getMood()));
            out.name("car");
            writeCar(out, human.getCar());
            out.name("user");
            writeUser(out, human.getUser());
            out.endObject();
        } else {
            out.value((String) null);
        }
    }

    private void writeCreationDate(JsonWriter out, HumanBeing human) throws IOException {
        if(human.getCreationDate() != null) {
            out.name("creationDate").value(human.getCreationDate().toString());
        } else {
            out.name("creationDate").value((String) null);
        }
    }

    public void writeCoordinates(JsonWriter out, Coordinates coo) throws IOException {
        if(coo != null) {
            out.beginObject();
            out.name("x");
            out.value(coo.getX());
            out.name("y");
            out.value(coo.getY());
            out.endObject();
        } else {
            out.value((String) null);
        }
    }

    private String valueEnum(Mood type) {
        if(type == null) return null;
        else return type.name();
    }

    public void writeCar(JsonWriter out, Car car) throws IOException {
        if(car != null) {
            out.beginObject();
            out.name("name").value(car.getCarName());
            out.name("cool").value(car.getCarCool());
            out.endObject();
        } else {
            out.value((String) null);
        }
    }

    public void writeUser(JsonWriter out, User user) throws IOException {
        if(user != null) {
            out.beginObject();
            out.name("username").value(user.getUsername());
            out.name("password").value(user.getPassword());
            out.name("colour").value(user.getColour());
            out.endObject();
        } else {
            out.value((String) null);
        }
    }

    public User readUser(JsonReader in) throws IOException {
        in.beginObject();
        String fieldName = null;
        String username = null;
        String password = null;
        String colour = null;

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

            if("colour".equals(fieldName)) {
                token = in.peek();
                colour = in.nextString();
            }
        }
        in.endObject();
        return new User(username, password, colour);
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

            if("creationDate".equals(fieldName)) {
                token = in.peek();
                human.setCreationDate(LocalDate.parse(in.nextString()));
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

            if("user".equals(fieldName)) {
                token = in.peek();
                human.setUser(readUser(in));
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
