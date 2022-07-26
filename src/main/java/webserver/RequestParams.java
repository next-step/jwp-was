package webserver;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class RequestParams {

    public static final int ONE_VALUE = 0;
    private MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

    public RequestParams(MultiValueMap<String, String> params) {
        this.params = params;
    }

    public String getOneValue(String key) {
        return params.get(key).get(ONE_VALUE);
    }
}
