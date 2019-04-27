package io.jaaj.network;

import io.jaaj.network.exception.ExceptionCannotDisconnect;
import io.jaaj.network.exception.ExceptionConnectionFailure;
import io.jaaj.network.exception.ExceptionPortInvalid;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class Example {
    public static void main(String[] args) throws ExceptionPortInvalid, IOException, InterruptedException {
        Thread t = new Thread(() -> {
            try {
                Server s = new Server(4040, new ServerRunnable() {
                    @Override
                    public void handle(Socket clientSocket) {
                        try {
                            String message = (String) receive();
                            send(message);
                        } catch (IOException | ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                });
                s.start();
            } catch (ExceptionPortInvalid | IOException e) {
                e.printStackTrace();
            }
        });
        t.start();

        Thread.sleep(1000);
        Client c = new Client(InetAddress.getByName("127.0.0.1"), 4040);
        try {
            c.connect();
            c.send("jeej");
            System.out.println(c.receive());
            c.disconnect();
        } catch (ExceptionConnectionFailure | ClassNotFoundException | ExceptionCannotDisconnect e) {
            e.printStackTrace();
        }

    }
}
