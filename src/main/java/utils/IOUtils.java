package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;

public class IOUtils {
    private static final StringBuilder stringBuilder = new StringBuilder();
    private static final ObjectMapper objectMapper = new ObjectMapper();
    public static final int INIT_POINT = 0;

    /**
     * @param br 는 Request Body를 시작하는 시점이어야
     * @param contentLength 는 Request Header의 Content-Length 값이다.
     * @return
     * @throws IOException
     */
    public static String readData(BufferedReader br, int contentLength) throws IOException {
        char[] body = new char[contentLength];
        br.read(body, 0, contentLength);
        return String.copyValueOf(body);
    }

    public static String readData(BufferedReader br) throws IOException {
        stringBuilder.setLength(INIT_POINT);

        for (String line, newLine = System.lineSeparator(); br.ready() && (line = br.readLine()) != null; ) {
            stringBuilder.append(line).append(newLine);
        }

        return stringBuilder.toString();
    }

    public static String writeData(Object o) throws JsonProcessingException {
        return objectMapper.writeValueAsString(o);
    }
}
