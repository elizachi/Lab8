package ru.itmo.server;

import ru.itmo.common.CommandType;
import ru.itmo.common.Request;
import ru.itmo.common.Response;
import ru.itmo.server.collection.commands.Command;
import ru.itmo.server.collection.commands.auth.Authorization;
import ru.itmo.server.collection.commands.auth.Registration;

public class HandleCommands {

    public Response handleRequest(Request request) {
        return executeCommand(
                request.getCommand(),
                request.getArguments()
        );
    }

    private Response executeCommand(CommandType command, Object arguments){
        int commandIndex = command.ordinal();
        Response response = commands[commandIndex].execute(arguments);
        ServerLauncher.log.info("Запрос успешно обработан");
        return response;
    }

    /**
     * existed commands
     */
    private static final Command[] commands = {
            new Authorization(),
            new Registration()
    };
}
