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
import dev.jaaj.network.exception.ExceptionServerRunnableNotEnded;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.InetAddress;

import static org.junit.Assert.assertEquals;

public class NetworkTest {
    private Client c;
    private Server s;

    @Before
    public void setUp() throws IOException, ExceptionPortInvalid, ExceptionConnectionFailure, InterruptedException {
        int port = 54324;
        Thread t = new Thread(() -> {
            try {
                s = new Server(port, new ServerRunnableEcho());
                s.start();
            } catch (IOException | ExceptionPortInvalid e) {
                e.printStackTrace();
            }
        });
        c = new Client(InetAddress.getByName("127.0.0.1"), port);
        t.start();
        Thread.sleep(1000);
        c.connect();
    }

    @After
    public void tearDown() throws ExceptionCannotDisconnect, ExceptionServerRunnableNotEnded {
        c.disconnect();
        s.stop();
    }

    @Test
    public void sendStringTest() throws Exception {
        String test = "test";
        c.send(test);
        String result = (String) c.receive();
        assertEquals(test, result);
    }

}
