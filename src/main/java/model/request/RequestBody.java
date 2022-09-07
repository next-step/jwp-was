package model.request;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class RequestBody {

    public static final int FIRST_VALUE = 0;
    public static final String DELIMITER = "?";
    private MultiValueMap<String, String> body = new LinkedMultiValueMap<>();

    public RequestBody(String body) throws UnsupportedEncodingException {
        body = URLDecoder.decode(body, "UTF-8");
        this.body = UriComponentsBuilder.fromUriString(DELIMITER + body).build().getQueryParams();
    }

    public String getFirstValue(String key) {
        return body.get(key).get(FIRST_VALUE);
    }

    @Override
    public String toString() {
        return "RequestBody{" +
                "body=" + body +
                '}';
    }
}
