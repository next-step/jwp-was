package webserver.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Header {
    private static final String CONTENT_LENGTH = "Content-Length";
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

    public int getContentLength() {
        return values.get(CONTENT_LENGTH) == null ? 0 : Integer.valueOf(values.get(CONTENT_LENGTH));
    }

    public String get(String key) {
        return values.get(key);
    }

    public void put(String key, String value) {
        values.put(key, value);
    }


    public List<String> toHeaderStrings() {
        return values.keySet()
                               .stream()
                               .map(key -> String.format("%s: %s", key, values.get(key)))
                               .collect(Collectors.toList());
    }
}
