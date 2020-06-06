package http.requestline.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class IllegalRequestLineParsingException extends IllegalArgumentException {

    public IllegalRequestLineParsingException(String s) {
        super(s);
    }
}
