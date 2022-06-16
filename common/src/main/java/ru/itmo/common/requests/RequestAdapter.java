package ru.itmo.common.requests;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import ru.itmo.common.general.CommandType;
import ru.itmo.common.general.CustomObjectAdapter;
import ru.itmo.common.general.User;
import ru.itmo.common.model.Car;
import ru.itmo.common.model.Coordinates;
import ru.itmo.common.model.HumanBeing;
import ru.itmo.common.model.Mood;

import java.io.IOException;
import java.time.LocalDate;

public class RequestAdapter extends TypeAdapter<Request> {
    CustomObjectAdapter adapter = new CustomObjectAdapter();
    @Override
    public void write(JsonWriter out, Request request) throws IOException {
        if(request != null) {
            out.beginObject();
            out.name("command");
            out.value(valueEnum(request.getCommand()));
            out.name("arguments");
            adapter.writeHuman(out, request.getArguments());
            out.name("user");
            adapter.writeUser(out, request.getUser());
            out.endObject();
        } else {
            out.value((String) null);
        }
    }

    private String valueEnum(CommandType type) {
        if(type == null) return null;
        else return type.name();
    }

    @Override
    public Request read(JsonReader in) throws IOException {
        in.beginObject();
        String fieldName = null;
        CommandType command = null;
        HumanBeing arg = null;
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
                arg = adapter.readHuman(in);
            }

            if("user".equals(fieldName)) {
                token = in.peek();
                user = adapter.readUser(in);
            }
        }
        in.endObject();
        return new Request(command, arg, user);
    }
}
