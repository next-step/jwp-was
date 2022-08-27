package webserver.request;

import webserver.cookie.Cookie;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Header {

    private static final String DELIMITER = ": ";
    private static final String BLANK = "";
    private static final int HEADER_NAME_IDX = 0;
    private static final int HEADER_VALUE_IDX = 1;

    private Map<String, String> headerMap = new HashMap<>();

    public Header() {}

    public Header(Map<String, String> headerMap) {
        this.headerMap = headerMap;
    }

    public static Header from(BufferedReader br) throws IOException {
        String line = br.readLine();

        Map<String, String> headerMap = new HashMap<>();
        while (!line.equals(BLANK)) {
            line = br.readLine();
            System.out.println(line);
            String[] values = line.split(DELIMITER);

            if (values.length == 2) {
                headerMap.put(values[HEADER_NAME_IDX], values[HEADER_VALUE_IDX]);
            }
        }

        return new Header(headerMap);
    }

    public void addHeader(String value) {
        String[] arr = value.split(DELIMITER);
        headerMap.put(arr[0], arr[1]);
    }

    public String getHeader(String header) {
        if(!headerMap.containsKey(header)) {
            return null;
        }
        return headerMap.get(header);
    }

    public Map<String, String> getHeaderMap() {
        return headerMap;
    }

    public void addCookie(Cookie cookie) {
        addHeader("Set-Cookie:" + cookie.cookiesToString());
    }
}
