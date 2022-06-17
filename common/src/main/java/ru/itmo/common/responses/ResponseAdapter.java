package ru.itmo.common.responses;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import ru.itmo.common.general.CustomObjectAdapter;
import ru.itmo.common.general.User;
import ru.itmo.common.model.HumanBeing;

import java.io.IOException;

public class ResponseAdapter extends TypeAdapter<Response>  {
    CustomObjectAdapter adapter = new CustomObjectAdapter();
    @Override
    public void write(JsonWriter out, Response response) throws IOException {
        if(response != null) {
            out.beginObject();
            out.name("status");
            out.value(valueEnum(response.getStatus()));
            out.name("answer");
            adapter.writeHuman(out, response.getAnswer());
            out.name("user");
            adapter.writeUser(out, response.getUser());
            out.endObject();
        } else {
            out.value((String) null);
        }
    }
    private String valueEnum(Response.Status type) {
        if(type == null) return null;
        else return type.name();
    }

    @Override
    public Response read(JsonReader in) throws IOException {
        in.beginObject();
        String fieldName = null;
        Response.Status status = null;
        HumanBeing ans = null;
        User user = null;

        while(in.hasNext()) {
            JsonToken token = in.peek();

            if(token.equals(JsonToken.NAME)) {
                fieldName = in.nextName();
            }

            if("status".equals(fieldName)) {
                token = in.peek();
                status = Response.Status.valueOf(in.nextString());
            }

            if("answer".equals(fieldName)) {
                token = in.peek();
                ans = adapter.readHuman(in);
            }

            if("user".equals(fieldName)) {
                token = in.peek();
                user = adapter.readUser(in);
            }
        }
        in.endObject();
        return new Response(status, ans, user);
    }
}
