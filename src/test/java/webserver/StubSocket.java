package webserver;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

public class StubSocket extends Socket {

    private final String request;
    private final ByteArrayOutputStream outputStream;

    public StubSocket(final String request) {
        this.request = request;
        this.outputStream = new ByteArrayOutputStream();
    }

    public StubSocket() {
        this("GET / HTTP/1.1\r\nHost: localhost:8080\r\n\r\n");
    }

    @Override
    public InetAddress getInetAddress() {
        try {
            return InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            return null;
        }
    }

    @Override
    public InputStream getInputStream() {
        return new ByteArrayInputStream(request.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public OutputStream getOutputStream() {
        return new OutputStream() {
            @Override
            public void write(int b) {
                outputStream.write(b);
            }
        };
    }

    public String output() {
        return outputStream.toString(StandardCharsets.UTF_8);
    }
}
