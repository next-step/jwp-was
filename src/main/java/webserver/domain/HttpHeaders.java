package webserver.domain;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.github.jknack.handlebars.internal.lang3.StringUtils.EMPTY;

public class HttpHeaders {
    public static final String DELIMITER = ": ";
    public static final int KEY_POINT = 0;
    public static final int VALUE_POINT = 1;
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String DEFAULT_CONTENT_TYPE = "text/html";
    public static final String CONTENT_LENGTH = "Content-Length";
    public static final String SET_COOKIE = "Set-Cookie";
    public static final String COOKIE = "Cookie";
    public static final String LOCATION = "Location";
    public static final String INVALID_HEADER_KEY_VALUE_MSG = "유효한 속성 문자열이 아닙니다. value:";

    private final Map<String, String> headers = new HashMap<>();

    public static HttpHeaders newInstance(String[] attributes, int start, int limit) {
        HttpHeaders httpHeaders = new HttpHeaders();

        IntStream.range(start, limit)
                .forEach(index -> httpHeaders.add(attributes[index]));

        return httpHeaders;
    }

    /**
     * 기본설정의 Http header 정보 객체를 반환한다.
     */
    public static HttpHeaders defaultResponseHeader() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(CONTENT_TYPE, DEFAULT_CONTENT_TYPE);

        return httpHeaders;
    }

    /**
     * Reader에서 헤더 정보를 가져와 Http Header 정보 객체를 만들어 반환한다.
     *
     * @param br header 정보를 포함하는 Reader
     * @return 헤더 정보
     */
    public static HttpHeaders from(BufferedReader br) throws IOException {
        HttpHeaders httpHeaders = new HttpHeaders();

        String line = br.readLine();
        while (line != null && !line.isBlank()) {
            httpHeaders.add(line.trim());
            line = br.readLine();
        }

        return httpHeaders;
    }

    /**
     * 문자열에서 헤더 이름과 속성을 분리해 헤더 정보를 추가한다.
     *
     * @param headerStr 헤더 정보 키/값 문자열
     */
    public void add(String headerStr) {
        if (Objects.isNull(headerStr)) {
            throw new IllegalArgumentException(INVALID_HEADER_KEY_VALUE_MSG + headerStr);
        }

        String[] headerInfo = headerStr.split(DELIMITER);

        if (headerInfo.length != 2) {
            throw new IllegalArgumentException(INVALID_HEADER_KEY_VALUE_MSG + headerStr);
        }

        headers.put(headerInfo[KEY_POINT].trim(), headerInfo[VALUE_POINT].trim());
    }

    /**
     * 헤더 정보를 추가한다.
     *
     * @param key 이름
     * @param value 속성
     */
    public void add(String key, String value) {
        headers.put(key, value);
    }


    /**
     * 헤더 속성을 찾아 반환한다.
     *
     * @param key 헤더 이름
     * @return 헤더 속성
     * @exception IllegalArgumentException 존재하지 않는 헤더 이름을 찾으려하면 예외를 던진다.
     */
    public String getAttribute(String key) {
        if (!headers.containsKey(key)) {
            throw new IllegalArgumentException();
        }
        return headers.get(key);
    }

    /**
     * 헤더 속성을 찾아 반환하는데, 없을 경우 인수로 전달받은 기본 값을 반환한다.
     *
     * @param key 헤더 이름
     * @param defaultValue 기본 값
     * @return 헤더 속성
     */
    public String getAttributeOrDefault(String key, String defaultValue) {
        return headers.getOrDefault(key, defaultValue);
    }

    /**
     * 헤더 이름이 Content-Length인 속성을 찾아 반환한다.
     */
    public int getContentLength() {
        String contentLength = getAttributeOrDefault(CONTENT_LENGTH, "0");
        return Integer.parseInt(contentLength);
    }

    /**
     * 헤더 이름이 Cookie인 속성을 찾아 쿠키 객체로 만들어 반환한다.
     * @return 쿠키 객체
     */
    public Cookie getCookie() {
        if (headers.containsKey(COOKIE)) {
            return Cookie.from(getAttribute(COOKIE));
        }
        return new Cookie();
    }

    @Override
    public String toString() {
        return headers.entrySet().stream()
                .map(entry -> entry.getKey() + ": " + entry.getValue() + "\r\n")
                .collect(Collectors.joining());
    }
}
