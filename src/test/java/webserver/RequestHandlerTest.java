package webserver;

import db.DataBase;
import model.User;
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
        when(socket.getInputStream()).thenReturn(new ByteArrayInputStream("POST /user/create HTTP/1.1\nContent-Length: 43\n\nuserid=ssosso&password=test&name=JangSoHyun".getBytes()));
        when(socket.getOutputStream()).thenReturn(new ByteArrayOutputStream());

        RequestHandler requestHandler = new RequestHandler(socket);
        requestHandler.run();

        InputStream responseStream = new ByteArrayInputStream(((ByteArrayOutputStream) socket.getOutputStream()).toByteArray());
        BufferedReader reader = new BufferedReader(new InputStreamReader(responseStream));
        assertThat(reader.readLine()).isEqualTo("HTTP/1.1 200 OK ");
    }

    @Test
    void runRedirect() throws IOException {
        DataBase.addUser(new User("ssosso", "test", "JangSoHyun", "ssossohow@gmail.com"));

        Socket socket = mock(Socket.class);
        when(socket.getInputStream()).thenReturn(new ByteArrayInputStream("PUT /user/update HTTP/1.1\nContent-Length: 43\n\nuserid=ssosso&password=test&name=JangSoHyun".getBytes()));
        when(socket.getOutputStream()).thenReturn(new ByteArrayOutputStream());

        RequestHandler requestHandler = new RequestHandler(socket);
        requestHandler.run();

        InputStream responseStream = new ByteArrayInputStream(((ByteArrayOutputStream) socket.getOutputStream()).toByteArray());
        BufferedReader reader = new BufferedReader(new InputStreamReader(responseStream));
        assertThat(reader.readLine()).isEqualTo("HTTP/1.1 302 Found");
        assertThat(reader.readLine()).isEqualTo("Location: /index.html");
    }

    @Test
    void when_user_login_then_exist_set_cookie_in_header() throws IOException {
        DataBase.addUser(new User("ssosso", "test", "JangSoHyun", "ssossohow@gmail.com"));

        Socket loginSocket = mock(Socket.class);
        when(loginSocket.getInputStream()).thenReturn(new ByteArrayInputStream("POST /user/login HTTP/1.1\nContent-Length: 27\n\nuserid=ssosso&password=test".getBytes()));
        when(loginSocket.getOutputStream()).thenReturn(new ByteArrayOutputStream());

        RequestHandler loginRequestHandler = new RequestHandler(loginSocket);
        loginRequestHandler.run();

        InputStream responseStream = new ByteArrayInputStream(((ByteArrayOutputStream) loginSocket.getOutputStream()).toByteArray());
        BufferedReader reader = new BufferedReader(new InputStreamReader(responseStream));
        assertThat(reader.readLine()).isEqualTo("HTTP/1.1 302 Found");
        assertThat(reader.readLine()).isEqualTo("Location: /index.html");
        assertThat(reader.readLine()).isEqualTo("Set-Cookie: logined=true; Path=/");
    }
}
