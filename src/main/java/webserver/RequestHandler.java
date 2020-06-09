package webserver;

import http.HttpRequest;
import http.RequestLine;
import http.RequestPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;

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
            HttpRequest httpRequest = new HttpRequest(in);
            RequestLine requestLine = httpRequest.getRequestLine();

            System.out.println("requestLine : " + requestLine.toString());
            RequestPath requestPath = requestLine.getRequestPath();

            if (requestPath.getPath().contains(".html")) {
                File file = new File("./src/main/resources/templates" + requestPath.getPath());
                System.out.println("./src/resources/templates" + requestPath.getPath());
                System.out.println("exists  : " + file.exists());

                FileReader fileReader = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(fileReader);

                StringBuffer stringBuffer = new StringBuffer();
                String response;
                while ((response = bufferedReader.readLine()) != null) {
                    stringBuffer.append(response);
                }

                DataOutputStream dos = new DataOutputStream(out);
                byte[] body = stringBuffer.toString().getBytes();
                response200Header(dos, body.length);
                responseBody(dos, body);
            }

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
