package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class IOUtils {

    private static final String END_OF_LINE = "";

    /**
     * @param BufferedReader는
     *            Request Body를 시작하는 시점이어야
     * @param contentLength는
     *            Request Header의 Content-Length 값이다.
     * @return
     * @throws IOException
     */
    public static String readData(BufferedReader br, int contentLength) throws IOException {
        char[] body = new char[contentLength];
        br.read(body, 0, contentLength);
        return String.copyValueOf(body);
    }

    public static List<String> readLines(BufferedReader br) throws IOException {
        List<String> strings = new ArrayList();
        String line = br.readLine();

        while (! line.equals(END_OF_LINE) || line == null) {
            line = br.readLine();
            strings.add(line);
        }

        return strings;
    }
}
