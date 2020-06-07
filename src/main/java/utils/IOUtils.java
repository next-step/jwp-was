package utils;

import http.request.Headers;
import http.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

public class IOUtils {
    private static final Logger logger = LoggerFactory.getLogger(IOUtils.class);
    private static final String PROTOCOL = "HTTP/1.1";
    private static final String DELIMITER_HEADER = ": ";

    /**
     * @param BufferedReader는 Request Body를 시작하는 시점이어야
     * @param contentLength는  Request Header의 Content-Length 값이다.
     * @return
     * @throws IOException
     */
    public static String readData(BufferedReader br, int contentLength) throws IOException {
        char[] body = new char[contentLength];
        br.read(body, 0, contentLength);
        return String.copyValueOf(body);
    }

    public static void writeData(Response response, OutputStream out) throws IOException {
        DataOutputStream dos = new DataOutputStream(out);
        responseHeader(dos, response);
        responseBody(dos, response.getBody());
    }

    private static void responseHeader(DataOutputStream dos, Response response) throws IOException {
        try {
            dos.writeBytes(PROTOCOL
                    + response.getStatus().toString()
                    + DELIMITER_HEADER
                    + response.getStatus().getStatusCode() + "\r\n");
            if (response.getHeaders() != null) {
                writeHeaders(dos, response.getHeaders());
                dos.writeBytes("\r\n");
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        dos.writeBytes("\r\n");
    }

    private static void writeHeaders(DataOutputStream dos, Headers headers) throws IOException {
        Set<String> keySet = headers.getKeySet();
        List<String> collect = keySet.stream().collect(toList());

        for (int i = 0; i < keySet.size(); i++) {
            dos.writeBytes(collect.get(i) + ": " + headers.getHeader(collect.get(i)) + "\r\n");
        }
    }

    private static void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
