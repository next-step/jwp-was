package service;

import model.HttpResponseMessage;
import model.HttpHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import types.HttpStatus;

import java.io.DataOutputStream;
import java.io.IOException;

public class ResponseService {
    private static final Logger logger = LoggerFactory.getLogger(ResponseService.class);

    public static void makeResponseHeader(DataOutputStream dataOutputStream, HttpResponseMessage httpResponseMessage) throws IOException {
        if (httpResponseMessage == null) {
            return;
        }

        byte[] body = httpResponseMessage.getBytesBody();

        HttpStatus responseHttpStatus = httpResponseMessage.getResponseHttpStatus();
        HttpHeaders responseHeaders = httpResponseMessage.getResponseHeaders();

        dataOutputStream.writeBytes(String.format("HTTP/1.1 %s %s\r\n", responseHttpStatus.getCode(), responseHttpStatus.name()));
        if (body != null) {
            dataOutputStream.writeBytes("Content-Length: " + body.length + "\r\n");
        }

        if (responseHeaders != null) {
            responseHeaders.getHeaders().keySet()
                    .forEach(key -> {
                        try {
                            String headerValue = responseHeaders.getHeaders().get(key);
                            dataOutputStream.writeBytes(key + ": " + headerValue + "\r\n");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        }
        dataOutputStream.writeBytes("\r\n");
    }

    public static void makeResponseBody(DataOutputStream dataOutputStream, HttpResponseMessage httpResponseMessage) throws IOException {
        if (httpResponseMessage == null) {
            dataOutputStream.flush();
            return;
        }

        byte[] body = httpResponseMessage.getBytesBody();
        if (body == null) {
            dataOutputStream.flush();
            return;
        }

        dataOutputStream.write(body, 0, body.length);
        dataOutputStream.flush();
    }

}
