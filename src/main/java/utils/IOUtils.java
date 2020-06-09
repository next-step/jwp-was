package utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.BufferedReader;
import java.io.IOException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class IOUtils {

    /**
     * @param bufferedReader 는 Request Body를 시작하는 시점이어야 한다.
     * @param contentLength  는 Request Header의 Content-Length 값이다.
     */
    public static String readData(BufferedReader bufferedReader, int contentLength) throws IOException {
        char[] body = new char[contentLength];
        bufferedReader.read(body, 0, contentLength);
        return String.copyValueOf(body);
    }
}
