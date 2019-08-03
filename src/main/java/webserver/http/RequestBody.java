package webserver.http;

import com.github.jknack.handlebars.internal.lang3.StringUtils;
import javafx.util.Pair;
import utils.IOUtils;
import utils.MapUtils;
import utils.StringDecoder;
import utils.StringParseUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RequestBody {
    private static final String REQUEST_BODY_SEPARATOR = "&";
    private static final String KEY_VALUE_SEPARATOR = "=";
    private Map<String, String> requestBody;

    private RequestBody(Map<String, String> requestBody) {
        this.requestBody = requestBody;
    }

    public static RequestBody parse(BufferedReader bufferedReader, HttpHeaders httpHeaders) throws IOException {
        return new RequestBody(getRequestBody(bufferedReader, httpHeaders));
    }

    private static Map<String, String> getRequestBody(BufferedReader bufferedReader, HttpHeaders httpHeaders) throws IOException {
        String requestBodyString = IOUtils.readData(bufferedReader, Integer.parseInt(httpHeaders.get("Content-Length")));
        return MapUtils.keyValueMap(Stream.of(StringDecoder.decode(requestBodyString).split(REQUEST_BODY_SEPARATOR)), KEY_VALUE_SEPARATOR);
    }

    @Override
    public String toString() {
        return this.requestBody.toString();
    }

    public String get(String key) {
        return this.requestBody.get(key);
    }
}
