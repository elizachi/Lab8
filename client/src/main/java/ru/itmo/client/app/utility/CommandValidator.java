package ru.itmo.client.app.utility;

import ru.itmo.client.app.exceptions.CheckHumanException;
import ru.itmo.client.app.exceptions.CommandException;
import ru.itmo.client.auth.exceptions.AuthException;
import ru.itmo.client.general.Client;
import ru.itmo.client.general.ClientLoader;
import ru.itmo.common.general.CommandType;
import ru.itmo.common.general.User;
import ru.itmo.common.model.Car;
import ru.itmo.common.model.Coordinates;
import ru.itmo.common.model.HumanBeing;
import ru.itmo.common.model.Mood;
import ru.itmo.common.requests.Request;
import ru.itmo.common.responses.Response;

public class CommandValidator {

    private final Client client = new Client(ClientLoader.getServerHost(), ClientLoader.getServerPort());

    public HumanBeing checkFields(CommandType type,
            String name, String soundtrackName, String minutesOfWaiting, String impactSpeed,
            boolean realHero, Boolean hasToothpick, String xCoo, String yCoo, String mood,
            String carName, boolean carCool, User user) throws CheckHumanException, CommandException {
        HumanBeing newHuman = new HumanBeing(
                checkNonNullFields(name),
                checkNonNullFields(soundtrackName),
                checkLongFields(minutesOfWaiting),
                checkIntFields(impactSpeed),
                realHero,
                hasToothpick,
                new Coordinates(checkLimitedInt(xCoo), checkLimitedFloat(yCoo)),
                convertToMood(mood),
                new Car(carName, carCool)
        );

        Response response = checkHuman(type, newHuman, user);

        return scanStatus(response);
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

    private Integer checkIntFields(String field) throws CheckHumanException{
        try {
            checkNonNullFields(field);
            return Integer.parseInt(field);
        } catch (NumberFormatException e) {
            throw new CheckHumanException(CheckHumanException.ErrorType.TYPE.setTitle(
                    "Поле должно содержать целое число"
            ));
        }
    }

    private Long checkLongFields(String field) throws CheckHumanException{
        try {
            checkNonNullFields(field);
            return Long.parseLong(field);
        } catch (NumberFormatException e) {
            throw new CheckHumanException(CheckHumanException.ErrorType.TYPE.setTitle(
                    "Поле должно содержать целое число"
            ));
        }
    }

    private Integer checkLimitedInt(String coo) throws CheckHumanException {
        Integer intCoo = checkIntFields(coo);
        if(Math.abs(intCoo) > 188) {
            throw new CheckHumanException(CheckHumanException.ErrorType.FORBIDDEN.setTitle(
                    "Поле может быть заполнено значением в диапазоне [-188; 188]"
            ));
        }
        return intCoo;
    }

    private Float checkLimitedFloat(String coo) throws CheckHumanException {
        Float floatCoo = checkFloatFields(coo);
        if(Math.abs(floatCoo) > 188) {
            throw new CheckHumanException(CheckHumanException.ErrorType.FORBIDDEN.setTitle(
                    "Поле может быть заполнено значением в диапазоне [-188.0; 188.0]"
            ));
        }
        return floatCoo;
    }

    private Float checkFloatFields(String field) throws CheckHumanException{
        try {
            checkNonNullFields(field);
            return Float.parseFloat(field);
        } catch (NumberFormatException e) {
            throw new CheckHumanException(CheckHumanException.ErrorType.TYPE.setTitle(
                    "Поле должно содержать дробное число"
            ));
        }
    }

    private Mood convertToMood(String mood) {
        if(mood.isEmpty()) return null;
        else {
            return Mood.valueOf(mood.toUpperCase());
        }
    }

    private HumanBeing scanStatus(Response response) throws CommandException {
        if(Response.Status.OK == response.getStatus()) {
            return new HumanBeing();
        } else if(Response.Status.UNKNOWN == response.getStatus()) {
            throw new CommandException();
        }
        return new HumanBeing();
    }

}
