package ru.itmo.common.requests;

import com.google.gson.Gson;
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

public class RequestAdapter extends TypeAdapter<Request> {
    @Override
    public void write(JsonWriter out, Request request) throws IOException {
        out.beginObject();
        out.name("command");
        out.value(valueEnum(request.getCommand()));
        out.name("arguments");
        writeHuman(out, request.getArguments());
        out.name("user");
        out.value(new Gson().toJson(request.getUser()));
        out.endObject();
    }

    private void writeHuman(JsonWriter out, HumanBeing human) throws IOException {
        out.beginObject();
        out.name("id").value(human.getId());
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
        out.endObject();
    }

    private void writeCoordinates(JsonWriter out, Coordinates coo) throws IOException {
        if(coo != null) {
            out.beginObject();
            out.name("x");
            out.value(coo.x);
            out.name("y");
            out.value(coo.y);
            out.endObject();
        } else {
            out.value((String) null);
        }
    }

    private void writeCar(JsonWriter out, Car car) throws IOException {
        if(car != null) {
            out.beginObject();
            out.name("name").value(car.getCarName());
            out.name("cool").value(car.getCarCool());
            out.endObject();
        } else {
            out.value((String) null);
        }
    }

    private String valueEnum(CommandType type) {
        if(type == null) return null;
        else return type.name();
    }

    private String valueEnum(Mood type) {
        if(type == null) return null;
        else return type.name();
    }

    @Override
    public Request read(JsonReader in) throws IOException {
        in.beginObject();
        String fieldName = null;
        CommandType command = null;
        Object arg = null;
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
        }
        in.endObject();
        return new Request(command, null, user);
    }

    private User readUser(JsonReader in) throws IOException {
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

    private HumanBeing readHuman(JsonReader in) throws IOException {
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

    private Car readCar(JsonReader in) throws IOException {
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

    private Coordinates readCoordinates(JsonReader in) throws IOException {
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
