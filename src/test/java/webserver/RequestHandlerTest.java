package webserver;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.Socket;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RequestHandlerTest {
    @Test
    void getUserCreate() throws IOException {
        Socket socket = mock(Socket.class);
        when(socket.getInputStream()).thenReturn(new ByteArrayInputStream("GET /user/create?userid=ssosso&password=test&name=JangSoHyun HTTP/1.1".getBytes()));
        when(socket.getOutputStream()).thenReturn(new ByteArrayOutputStream());

        RequestHandler requestHandler = new RequestHandler(socket);
        requestHandler.run();

        InputStream responseStream = new ByteArrayInputStream(((ByteArrayOutputStream) socket.getOutputStream()).toByteArray());
        BufferedReader reader = new BufferedReader(new InputStreamReader(responseStream));
        assertThat(reader.readLine()).isEqualTo("HTTP/1.1 200 OK ");
    }

    @Test
    void postUserCreate() throws IOException {
        Socket socket = mock(Socket.class);
        when(socket.getInputStream()).thenReturn(new ByteArrayInputStream("POST /user/create HTTP/1.1\n\nuserid=ssosso&password=test&name=JangSoHyun".getBytes()));
        when(socket.getOutputStream()).thenReturn(new ByteArrayOutputStream());

        RequestHandler requestHandler = new RequestHandler(socket);
        requestHandler.run();

        InputStream responseStream = new ByteArrayInputStream(((ByteArrayOutputStream) socket.getOutputStream()).toByteArray());
        BufferedReader reader = new BufferedReader(new InputStreamReader(responseStream));
        assertThat(reader.readLine()).isEqualTo("HTTP/1.1 200 OK ");
    }
}
