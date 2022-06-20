package webserver.http.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Header {
    private final Map<String, String> values;

    public Header(Map<String, String> values) {
        this.values = values;
    }

    public static Header of(BufferedReader bufferedReader) throws IOException {
        Map<String, String> values = new HashMap<>();
        String readLine;
        do {
            readLine = bufferedReader.readLine();
            if (readLine == null || "".equals(readLine)) {
                break;
            }
            String[] tokens = readLine.split(": ");
            values.put(tokens[0], tokens[1]);
        } while (!"".equals(readLine));

        return new Header(values);
    }
}
