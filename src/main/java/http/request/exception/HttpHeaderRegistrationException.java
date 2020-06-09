package http.request.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class HttpHeaderRegistrationException extends IllegalArgumentException {

    public HttpHeaderRegistrationException(String s) {
        super(s);
    }
}
