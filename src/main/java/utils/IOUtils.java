package utils;

import java.io.BufferedReader;
import java.io.IOException;

public class IOUtils {
    /**
     * @param bufferedReader 는
     *            Request Body를 시작하는 시점이어야
     * @param contentLength 는
     *            Request Header의 Content-Length 값이다.
     * @return
     * @throws IOException
     */
    public static String readData(final BufferedReader bufferedReader, final int contentLength) throws IOException {
        char[] body = new char[contentLength];

        bufferedReader.read(body, 0, contentLength);

        return String.copyValueOf(body);
    }
}
