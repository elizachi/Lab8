package ru.itmo.server.general;

import ru.itmo.common.requests.Request;
import ru.itmo.common.responses.Response;
import ru.itmo.server.utility.HandleCommands;
import ru.itmo.server.ServerLauncher;

import java.io.IOException;
import java.net.BindException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Server {
    private boolean work = true;
    private ServerSocketChannel serverSocketChannel;
    private final Set<SocketChannel> session;
    private final InetSocketAddress address;
    private Selector selector;

    private final ExecutorService executorService = Executors.newFixedThreadPool(5);
    private final Lock lock = new ReentrantLock();

    public Server(String host, int port) {
        this.address = new InetSocketAddress(host, port);
        this.session = new HashSet<SocketChannel>();
        runSocketChannel();
    }

    public void start() {
        while(work) {
            try {
                selector.select();

                selectorGo(selector.selectedKeys());

            } catch(IOException e) {
                ServerLauncher.log.error("Что-то пошло не так во время работы селектора");
                work = false;
            }
        }
        stopSocketChannel();
    }
    private void selectorGo(Set<SelectionKey> setKeys) {
        Iterator<SelectionKey> keys = setKeys.iterator();

        while(keys.hasNext()) {

            SelectionKey key = keys.next();
            keys.remove();

            if(!key.isValid()) {
                continue;
            } if(key.isAcceptable()) {
                accept(key);
            } if(key.isReadable()) {
                try {

                    // чтение реквеста от клиента
                    SocketChannel channel = (SocketChannel) key.channel();
                    ByteBuffer byteBuffer = ByteBuffer.allocate(4096);

                    int amount = channel.read(byteBuffer);

                    if(amount == -1) {
                        session.remove(channel);

//                        if(session.size() == 0) {
//                            work = false;
//                        }

                        ServerLauncher.log.info("Клиент " +
                                channel.socket().getRemoteSocketAddress() +" вышел из приложения");
                        channel.close();
                        key.cancel();
                        return;
                    }

                    byte[] data = new byte[amount];
                    System.arraycopy(byteBuffer.array(), 0, data, 0, amount);

                    String json = new String(data, StandardCharsets.UTF_8);

                    Request request = Request.fromJson(json);

                    ServerLauncher.log.info("Запрос на выполнение команды "
                                + request.getCommand().name().toLowerCase());

                    // Обработка реквеста
                    Boolean result = handler(key, request);
                    if(result == null) {
                        key.cancel();
                    } else if(!result) {
                        work = false;
                    }
                } catch (IOException e) {
                    ServerLauncher.log.info("Произошла ошибка во время работы селектора");
                    work = false;
                }
            }
        }
    }
    private Boolean handler(SelectionKey key, Request request) {
        if(request == null) {
            return null;
        } else {

            Response response = new HandleCommands().handleRequest(request);

                ServerLauncher.log.info("Начата отправка результата выполнения запроса клиенту");
                SocketChannel channel = (SocketChannel) key.channel();

                try {
                    int dataSize = response.toJson().getBytes(StandardCharsets.UTF_8).length;
                    int count = dataSize/4096 + (dataSize%4096 == 0 ? 0 : 1);
                    String countPackage = Integer.toString(count);
                    channel.write(ByteBuffer.wrap(countPackage.getBytes(StandardCharsets.UTF_8)));

                    //отправка респонза клиенту
                    if(dataSize > 4096) {
                        for(int i = 0; i < dataSize; i+=4096) {
                            if(i + 4096 > dataSize) {
                                channel.write(ByteBuffer.wrap(
                                        Arrays.copyOfRange(response.toJson().getBytes(StandardCharsets.UTF_8), i, dataSize))
                                );
                            } else {
                                channel.write(ByteBuffer.wrap(
                                        Arrays.copyOfRange(response.toJson().getBytes(StandardCharsets.UTF_8), i, i+4096))
                                );
                            }
                        }
                    } else {
                        channel.write(ByteBuffer.wrap(response.toJson().getBytes(StandardCharsets.UTF_8)));
                    }
                    ServerLauncher.log.info("Отправка выполнена успешно");

                } catch (IOException e) {
                    ServerLauncher.log.error("Отправка не удалась");
                }
            return true;
        }
    }
    private void runSocketChannel() {
        try {
            ServerLauncher.log.info("Запуск сервера...");
            selector = Selector.open();
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(address);
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            ServerLauncher.log.info("Сервер успешно запущен");

            ServerLoader.setConnection();
//            ArrayDequeDAO.getInstance().setCollection(
//                    new PostgreSqlDao().getAll()
//            );
        } catch (ClosedChannelException e) {
            ServerLauncher.log.fatal("Сервер был принудительно закрыт");
            return;
        } catch(BindException e) {
            ServerLauncher.log.fatal("На исходных хосте и порту уже запущен сервер");
            return;
        } catch (IOException e) {
            ServerLauncher.log.fatal("Ошибка запуска сервера");
            return;
        }
    }
    private void stopSocketChannel() {
        try {
            ServerLauncher.log.info("Закрытие сервера...");
            serverSocketChannel.close();
            ServerLauncher.log.info("Сервер успешно закрыт");
        } catch (IOException e) {
            ServerLauncher.log.error("Ошибка закрытия сервера");
        }
    }

    private void accept(SelectionKey key) {
        try {
            ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
            SocketChannel channel = serverSocketChannel.accept();
            channel.configureBlocking(false);
            channel.register(selector, SelectionKey.OP_READ);
            session.add(channel);
            ServerLauncher.log.info("Клиент "+channel.socket().getRemoteSocketAddress()+
                    " успешно подсоединился к серверу");
        } catch (IOException e) {
            ServerLauncher.log.error("Ошибка селектора");
        }
    }
}
