package http.request.querystring.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class QueryStringParsingException extends IllegalArgumentException {

    public QueryStringParsingException(String s) {
        super(s);
    }
}
