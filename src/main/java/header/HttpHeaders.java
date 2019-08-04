package header;

import response.HeaderResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by youngjae.havi on 2019-08-04
 */
public class HttpHeaders {
    private Map<String, HeaderResponse> httpHeaders;

    private HttpHeaders(Map<String, HeaderResponse> httpHeaders) {
        this.httpHeaders = httpHeaders;
    }

    public static HttpHeaders of(HeaderResponse... headerResponses) {
        return new HttpHeaders(Arrays.stream(headerResponses)
                .collect(Collectors.toMap(HeaderResponse::key, o -> o))
        );
    }

    public List<HeaderResponse> toList() {
        return new ArrayList<>(httpHeaders.values());
    }
}
