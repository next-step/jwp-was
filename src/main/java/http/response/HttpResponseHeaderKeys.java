package http.response;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HttpResponseHeaderKeys {

    public static final String COOKIE_HEADER_KEY = "Set-Cookie";
    public static final String CONTENT_TYPE_HEADER_KEY = "Content-Type";
    public static final String CONTENT_LENGTH_HEADER_KEY = "Content-Length";
    public static final String LOCATION_HEADER_KEY = "Location";
}
