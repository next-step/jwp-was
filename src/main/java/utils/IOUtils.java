package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IOUtils {

    private static final Logger logger = LoggerFactory.getLogger(IOUtils.class);

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

    public static List<String> readLines(BufferedReader bufferedReader) {
        List<String> lines = new ArrayList<>();

        try {
            String line = bufferedReader.readLine();
            while (line != null && !line.isEmpty()) {
                lines.add(line);
                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new IllegalArgumentException("요청을 읽을 수 없습니다.", e);
        }

        return lines;
    }

    public static String readSingleLine(BufferedReader bufferedReader) {
        try {
            return bufferedReader.readLine();
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }
}
