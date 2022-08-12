package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class IOUtils {
    /**
     * @param BufferedReader는
     *            Request Body를 시작하는 시점이어야
     * @param contentLength는
     *            Request Header의 Content-Length 값이다.
     * @return
     * @throws IOException
     */
    private static final Logger logger = LoggerFactory.getLogger(IOUtils.class);

    public static String readData(BufferedReader br, int contentLength) throws IOException {
        char[] body = new char[contentLength];
        br.read(body, 0, contentLength);
        return String.copyValueOf(body);
    }

    public static String readRequestData(BufferedReader br) {

        try {
            return br.readLine();

        } catch (IOException e) {
            logger.error(e.getMessage());
        }

        throw new IllegalArgumentException("RequestLine을 읽는데 실패했습니다.");
    }

    public static List<String> readHeaderData(BufferedReader br) {
        List<String> data = new ArrayList<>();

        try {
            String line = br.readLine();

            while (line != null && !line.isEmpty()) {
                data.add(line);
                line = br.readLine();
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return data;
    }
}