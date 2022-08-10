package model;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class RequestBody {

    public static final int ONE_VALUE = 0;
    public static final String DELIMITER = "?";
    private MultiValueMap<String, String> body = new LinkedMultiValueMap<>();

    public RequestBody(String body) throws UnsupportedEncodingException {
        body = URLDecoder.decode(body, "UTF-8");
        this.body = UriComponentsBuilder.fromUriString(DELIMITER + body).build().getQueryParams();
    }

    public String getOneValue(String key) throws UnsupportedEncodingException {
        return body.get(key).get(ONE_VALUE);
    }

    @Override
    public String toString() {
        return "RequestBody{" +
                "body=" + body +
                '}';
    }
}
