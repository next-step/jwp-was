package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class IOUtils {
    /**
     * @param br 는
     *            Request Body를 시작하는 시점이어야
     * @param contentLength 는
     *            Request Header의 Content-Length 값이다.
     * @return
     * @throws IOException
     */
    public static String readData(BufferedReader br, int contentLength) throws IOException {
        char[] body = new char[contentLength];
        br.read(body, 0, contentLength);
        return URLDecoder.decode(String.copyValueOf(body), "UTF-8");
    }

    public static List<String> readLines(BufferedReader br) throws IOException {
        List<String> reuqestLine = new ArrayList<>();
        String line = br.readLine();
        while(line != null && !line.equals("")) {
            reuqestLine.add(URLDecoder.decode(line, "UTF-8"));
            line = br.readLine();
        }

        return reuqestLine;
    }

}
