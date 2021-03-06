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

import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;

public abstract class ServerRunnable implements Runnable, Cloneable {

    protected Socket clientSocket;

    void setClientSocket(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public final void run() {
        handle(clientSocket);
    }

    public abstract void handle(Socket clientSocket);

    protected void send(Serializable serializable) throws IOException {
        util.sendSerializable(clientSocket, serializable);
    }

    protected Serializable receive() throws IOException, ClassNotFoundException {
        return util.receiveSerializable(clientSocket);
    }
}
