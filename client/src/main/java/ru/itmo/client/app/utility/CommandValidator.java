package ru.itmo.client.app.utility;

import ru.itmo.client.app.exceptions.CheckHumanException;
import ru.itmo.client.app.exceptions.CommandException;
import ru.itmo.client.general.Client;
import ru.itmo.client.general.ClientLoader;
import ru.itmo.common.general.CommandType;
import ru.itmo.common.general.User;
import ru.itmo.common.model.HumanBeing;
import ru.itmo.common.model.Mood;
import ru.itmo.common.requests.Request;
import ru.itmo.common.responses.Response;

import java.time.LocalDate;

public class CommandValidator {

    private final Client client = new Client(ClientLoader.getServerHost(), ClientLoader.getServerPort());

    public void checkFields(CommandType type, HumanBeing newHuman, User currentUser) throws CommandException {

        newHuman.setCreationDate(LocalDate.now());
        Response response = checkHuman(type, newHuman, currentUser);
        scanStatus(response);

    }
    private Response checkHuman(CommandType type, HumanBeing human, User user) {
        Request request = new Request(
                type,
                human,
                user
        );
        return client.send(request);
    }

    public String checkNonNullFields(String field) throws CheckHumanException {
        if(field.isEmpty()) throw new CheckHumanException(
                CheckHumanException.ErrorType.EMPTY.setTitle(
                                "не заполнено"
                )
        );
        return field;
    }

    public Integer checkIntFields(String field) throws CheckHumanException{
        try {
            checkNonNullFields(field);
            return Integer.parseInt(field);
        } catch (NumberFormatException e) {
            throw new CheckHumanException(CheckHumanException.ErrorType.TYPE.setTitle(
                    "Поле должно содержать целое число"
            ));
        }
    }

    public Long checkLongFields(String field) throws CheckHumanException{
        try {
            checkNonNullFields(field);
            return Long.parseLong(field);
        } catch (NumberFormatException e) {
            throw new CheckHumanException(CheckHumanException.ErrorType.TYPE.setTitle(
                    "Поле должно содержать целое число"
            ));
        }
    }

    public Integer checkLimitedInt(String coo) throws CheckHumanException {
        Integer intCoo = checkIntFields(coo);
        if(Math.abs(intCoo) > 188) {
            throw new CheckHumanException(CheckHumanException.ErrorType.FORBIDDEN.setTitle(
                    "Поле может быть заполнено значением в диапазоне [-188; 188]"
            ));
        }
        return intCoo;
    }

    public Float checkLimitedFloat(String coo) throws CheckHumanException {
        Float floatCoo = checkFloatFields(coo);
        if(Math.abs(floatCoo) > 188) {
            throw new CheckHumanException(CheckHumanException.ErrorType.FORBIDDEN.setTitle(
                    "Поле может быть заполнено значением в диапазоне [-188.0; 188.0]"
            ));
        }
        return floatCoo;
    }

    public Float checkFloatFields(String field) throws CheckHumanException{
        try {
            checkNonNullFields(field);
            return Float.parseFloat(field);
        } catch (NumberFormatException e) {
            throw new CheckHumanException(CheckHumanException.ErrorType.TYPE.setTitle(
                    "Поле должно содержать дробное число"
            ));
        }
    }

    public Mood convertToMood(String mood) {
        if(mood.isBlank()) return null;
        else {
            return Mood.valueOf(mood.toUpperCase());
        }
    }

    private void scanStatus(Response response) throws CommandException {
        if(Response.Status.OK == response.getStatus()) {
            return;
        } else {
            throw new CommandException();
        }
    }

}
