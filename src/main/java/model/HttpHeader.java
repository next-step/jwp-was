package model;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HttpHeader {

    public static final String DELIMITER = ": ";
    public static final int NAME_INDEX = 0;
    public static final int VALUE_INDEX = 1;
    public static final int NO_CONTENT = 0;
    public static final String CONTENT_LENGTH = "Content-Length";
    public static final int REQUEST_LINE_INDEX = 0;
    public static final String COOKIE = "Cookie";
    public static final String NO_COOKIE = null;
    public static final String HOST = "Host";
    public static final int PORT_INDEX = 2;
    public static final String DELIMETER = ": ";

    private Map<String, String> headers = new HashMap<>();

    public HttpHeader() { }

    public HttpHeader(List<String> headers) {
        this.headers = headers.stream()
                .map(header -> header.split(DELIMITER))
                .collect(Collectors.toMap(item -> item[0], item -> item[1].strip()));
    }

    public String getValue(String name) {
        return headers.get(name);
    }

    public int getValueToInt(String name) {
        final String value = getValue(name);
        if (value == null) {
            return 0;
        }
        return Integer.parseInt(value);
    }

    public void addLocation(String location) {
        headers.put("Location", location);

    }

    public void addValue(String name, String value) {
        headers.put(name, value);
    }

    public void addContentLength(int length) {
        headers.put("Content-Length", String.valueOf(length));
    }

    public void addContentType(String contentType) {
        headers.put("Content-Type", contentType + ";charset=utf-8");
    }

    public void addCookie(String name, String value) {
        headers.put("Set-Cookie", name + "=" + value);
    }

    public void writeOutput(DataOutputStream dos) throws IOException {
        headers.keySet().stream()
                .forEach(name -> {
                    try {
                        dos.writeBytes(name + DELIMETER + getValue(name));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
        dos.writeBytes("\n\n");
    }

    @Override
    public String toString() {
        return "HttpHeader{" +
                "headers=" + headers +
                '}';
    }
}
