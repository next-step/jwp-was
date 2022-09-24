package webserver.http.domain;

import org.springframework.util.StringUtils;
import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static webserver.http.domain.RequestParams.KEY_VALUE_DELIMITER;

public class RequestBody {

    private static final String BODY_DELIMITER = "&";
    public static final String EMPTY_STRING = "";
    private final Map<String, String> bodies;

    public RequestBody(String bodyString) {
        this.bodies = create(bodyString);
    }

    public RequestBody(BufferedReader br, RequestHeader requestHeader) {
        String value = requestHeader.getValue("Content-Length");

        if (StringUtils.hasText(value)) {
            try {
                String body = IOUtils.readData(br, Integer.parseInt(value));
                create(decode(body));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        this.bodies = new HashMap<>();
    }

    public String body(String key) {
        return bodies.get(key);
    }

    private Map<String, String> create(String bodyString) {
        Map<String, String> map = new HashMap<>();
        Arrays.stream(bodyString.split(BODY_DELIMITER))
                .map(param -> param.split(KEY_VALUE_DELIMITER))
                .forEach(params -> {
                    putParams(map, params);
                });
        return map;
    }

    private void putParams(Map<String, String> map, String[] params) {
        if (params.length == 1) {
            map.put(params[0], EMPTY_STRING);
            return;
        }
        map.put(params[0], params[1]);
    }

    private String decode(String value) {
        return URLDecoder.decode(value, StandardCharsets.UTF_8);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestBody that = (RequestBody) o;
        return Objects.equals(bodies, that.bodies);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bodies);
    }
}
