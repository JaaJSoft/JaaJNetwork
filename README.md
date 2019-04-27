# JaaJNetwork

A simple network library for Java

## Example

### Server - Echo

```java
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
```

### Client - Echo
```java
Client c = new Client(InetAddress.getByName("127.0.0.1"), 4040);
try {
    c.connect();
    c.send("jeej"); // you can send anything that is serializable
    System.out.println(c.receive());
    c.disconnect();
} catch (ExceptionConnectionFailure | ClassNotFoundException | ExceptionCannotDisconnect e) {
    e.printStackTrace();
}
```

