/*
 * Copyright 2020 JaaJSoft
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.jaaj.network;

import dev.jaaj.network.exception.ExceptionCannotDisconnect;
import dev.jaaj.network.exception.ExceptionConnectionFailure;
import dev.jaaj.network.exception.ExceptionPortInvalid;

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
