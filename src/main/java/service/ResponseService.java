package service;

import model.ClientResponse;
import model.HttpHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import types.HttpStatus;

import java.io.DataOutputStream;
import java.io.IOException;

public class ResponseService {
    private static final Logger logger = LoggerFactory.getLogger(ResponseService.class);

    public static void makeResponseHeader(DataOutputStream dataOutputStream, ClientResponse clientResponse) throws IOException {
        if (clientResponse == null) {
            return;
        }

        byte[] body = clientResponse.getBytesBody();

        HttpStatus responseHttpStatus = clientResponse.getResponseHttpStatus();
        HttpHeaders responseHeaders = clientResponse.getResponseHeaders();

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

    public static void makeResponseBody(DataOutputStream dataOutputStream, ClientResponse clientResponse) throws IOException {
        if (clientResponse == null) {
            dataOutputStream.flush();
            return;
        }

        byte[] body = clientResponse.getBytesBody();
        if (body == null) {
            dataOutputStream.flush();
            return;
        }

        dataOutputStream.write(body, 0, body.length);
        dataOutputStream.flush();
    }

}
