package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class IOUtils {

    /**
     * @param bufferedReader 빈문자열 라인을 포함한 문자열을 입력받는 bufferedReader.
     *                       라인은 개행문자를 기준으로 구분된다.
     * @return buffer에서 공백라인 직전 라인까지 읽은 라인별 문자열의 목록.
     * @throws IOException
     */
    public static List<String> readWhileEmptyLine(BufferedReader bufferedReader) throws IOException {
        List<String> lines = new ArrayList<>();
        String line;
        while((line = bufferedReader.readLine()) != null && !line.isEmpty()) {
            lines.add(line);
        }
        return lines;
    }

    /**
     * @param bufferedReader Request Body를 시작하는 시점이어야 한다.
     * @param contentLength Request Header의 Content-Length 값이다.
     * @return buffer에서 Content-Length 만큼 읽어온 문자열
     * @throws IOException
     */
    public static String readData(BufferedReader bufferedReader, int contentLength) throws IOException {
        char[] body = new char[contentLength];
        bufferedReader.read(body, 0, contentLength);
        return String.copyValueOf(body);
    }
}
