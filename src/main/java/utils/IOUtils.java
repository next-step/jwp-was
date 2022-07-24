package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class IOUtils {
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

    public static List<String> readLines(BufferedReader br) throws IOException {
        List<String> readLines = new ArrayList<>();

        String readLine = "";
        do {
            readLine = strictlyRead(br);
            readLines.add(readLine);
        } while (!readLine.isEmpty());

        return readLines.stream()
                .filter((it) -> !it.isEmpty())
                .collect(Collectors.toList());
    }

    private static String strictlyRead(BufferedReader br) throws IOException {
        String readLine = br.readLine();
        if (readLine == null || readLine.isEmpty()) {
            return "";
        }

        return readLine;
    }
}
