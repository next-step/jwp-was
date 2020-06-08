package mock;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.Socket;

public class MockSocket extends Socket {

    public String mockRequestLine;

    public MockSocket() {
    }

    public MockSocket(String mockRequestLine) {
        this.mockRequestLine = mockRequestLine;
    }

    public InputStream getInputStream() {
        return new ByteArrayInputStream(mockRequestLine.getBytes());
    }
}
