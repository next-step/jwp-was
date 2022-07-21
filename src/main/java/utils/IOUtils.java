package utils;

import java.io.BufferedReader;
import java.io.IOException;
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
    public static String readData(BufferedReader br, int contentLength) throws IOException {
        char[] body = new char[contentLength];
        br.read(body, 0, contentLength);
        return String.copyValueOf(body);
    }

    /**
     * 라인 리스트 반환
     * @param BufferedReader
     * @return
     * @throws IOException
     */
    public static List<String> readData(BufferedReader br) throws IOException {
        List<String> lines = new ArrayList<>();
        String line;

        while((line = br.readLine()) != null && !line.isEmpty()) {
            lines.add(line);
        }
        return lines;
    }
}
