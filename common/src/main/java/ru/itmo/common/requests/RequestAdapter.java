package ru.itmo.common.requests;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import ru.itmo.common.general.CommandType;
import ru.itmo.common.general.User;

import java.io.IOException;

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
                arg = in.nextString();
            }

            if("user".equals(fieldName)) {
                token = in.peek();
                user = readUser(in);
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
}
