package ru.itmo.client.app.utility;

import ru.itmo.client.general.Client;
import ru.itmo.client.general.ClientLoader;
import ru.itmo.common.general.CommandType;
import ru.itmo.common.general.User;
import ru.itmo.common.model.HumanBeing;
import ru.itmo.common.requests.Request;

import java.util.ArrayDeque;
import java.util.Deque;

public class LoadData {

    private final Client client = new Client(ClientLoader.getServerHost(), ClientLoader.getServerPort());

    public Deque<HumanBeing> load() {

        Deque<HumanBeing> humanArrayDeque = new ArrayDeque<>();
        HumanBeing result = client.send(new Request(CommandType.LOAD, null, null)).getAnswer();

            String[] array = result.getName().trim().split(" ");

            for (String numbers : array) {
                int id = Integer.parseInt(numbers);

                HumanBeing temp = new HumanBeing();
                temp.setId(id);

                HumanBeing human = client.send(new Request(CommandType.GET,
                        temp, null)).getAnswer();

                humanArrayDeque.add(human);
            }
            return humanArrayDeque;
    }

}
