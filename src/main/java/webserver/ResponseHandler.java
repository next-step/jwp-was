package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import webserver.http.PathMapper;
import webserver.http.RequestLine;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;

public class ResponseHandler {
    private static final Logger logger = LoggerFactory.getLogger(ResponseHandler.class);

    public static void response200(OutputStream out, RequestLine requestLine) {
        try {
            DataOutputStream doc = new DataOutputStream(out);
            byte[] body = FileIoUtils.loadFileFromClasspath(requestLine.getFilePath());

            doc.writeBytes("HTTP/1.1 200 OK \r\n");
            doc.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            doc.writeBytes("Content-Length: " + body.length + "\r\n");
            doc.writeBytes("\r\n");

            responseBody(doc, body);
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    public static void response302(OutputStream out, RequestLine requestLine) {
        try {
            DataOutputStream doc = new DataOutputStream(out);
            byte[] body = FileIoUtils.loadFileFromClasspath(requestLine.getFilePath());

            doc.writeBytes("HTTP/1.1 302 Found \r\n");
            doc.writeBytes("Location: " + PathMapper.findByKey(requestLine.getPath().getPath()) + "\r\n");
            doc.writeBytes("\r\n");

            responseBody(doc, body);
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    public static void response302WithCookie(OutputStream out, RequestLine requestLine, String cookie) {
        try {
            DataOutputStream doc = new DataOutputStream(out);
            byte[] body = FileIoUtils.loadFileFromClasspath(requestLine.getFilePath());

            doc.writeBytes("HTTP/1.1 302 Found \r\n");
            doc.writeBytes("Location: " + PathMapper.findByKey(requestLine.getPath().getPath()) + "\r\n");
            doc.writeBytes(cookie);
            doc.writeBytes("\r\n");

            responseBody(doc, body);
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private static void responseBody(DataOutputStream doc, byte[] body) {
        try {
            doc.write(body, 0, body.length);
            doc.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
