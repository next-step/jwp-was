package http.request.requestline.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class RequestLineParsingException extends IllegalArgumentException {

    public RequestLineParsingException(String s) {
        super(s);
    }
}
