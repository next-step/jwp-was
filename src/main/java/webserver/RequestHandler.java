package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.request.Header;
import webserver.http.request.HttpRequest;
import webserver.http.request.RequestBody;
import webserver.http.request.RequestLine;
import webserver.http.response.HttpResponse;
import webserver.http.service.Service;
import webserver.http.service.UserCreateGetService;
import webserver.http.service.UserCreatePostService;
import webserver.http.service.ViewService;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
            RequestLine requestLine = RequestLine.parse(bufferedReader.readLine());
            Header header = Header.of(bufferedReader);
            RequestBody requestBody = RequestBody.of(bufferedReader, header.getContentLength());

            HttpRequest httpRequest = new HttpRequest(requestLine, header, requestBody);
            HttpResponse httpResponse = new HttpResponse();

            doService(httpRequest, httpResponse);

            DataOutputStream dos = new DataOutputStream(out);
            responseHeader(dos, httpResponse);
            responseBody(dos, httpResponse);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void doService(HttpRequest httpRequest, HttpResponse httpResponse) {
        ViewService viewService = new ViewService();
        UserCreateGetService userCreateGetService = new UserCreateGetService();
        UserCreatePostService userCreatePostService = new UserCreatePostService();
        List<Service> services = List.of(viewService, userCreateGetService, userCreatePostService);

        Service service = services.stream()
                                  .filter(it -> it.find(httpRequest))
                                  .findFirst()
                                  .orElseThrow();
        service.doService(httpRequest, httpResponse);
    }

    private void responseHeader(DataOutputStream dos, HttpResponse httpResponse) {
        try {
            if (httpResponse.getStatus() == 200) {
                dos.writeBytes("HTTP/1.1 200 OK \r\n");
                dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
                dos.writeBytes("Content-Length: " + httpResponse.getLength() + "\r\n");
                dos.writeBytes("\r\n");
            }

            if (httpResponse.getStatus() == 302) {
                dos.writeBytes("HTTP/1.1 302 OK \r\n");
                dos.writeBytes("Location: " + httpResponse.getLocation() + " \r\n");
                dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
                dos.writeBytes("Content-Length: " + httpResponse.getLength() + "\r\n");
                dos.writeBytes("\r\n");
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, HttpResponse httpResponse) {
        try {
            dos.write(httpResponse.getBody(), 0, httpResponse.getLength());
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
