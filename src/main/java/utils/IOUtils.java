package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class IOUtils {
    /**
     * @param BufferedReader는
     *            RequestHolder Body를 시작하는 시점이어야
     * @param contentLength는
     *            RequestHolder Header의 Content-Length 값이다.
     * @return
     * @throws IOException
     */
    public static String readData(BufferedReader br, int contentLength) throws IOException {
        char[] body = new char[contentLength];
        br.read(body, 0, contentLength);
        return String.copyValueOf(body);
    }

    public static List<String> readLines(BufferedReader reader) throws IOException {
        List<String> lines = new ArrayList<>();

        String line;
        while (reader.ready() && StringUtils.isNotBlank(line = reader.readLine())) {
            lines.add(line);
        }

        return lines;
    }

}
