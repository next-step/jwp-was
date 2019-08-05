package webserver.http.request;

import lombok.Getter;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Getter
public class MessageBody {

    private final Query query;
    private final String messageBody;

    public MessageBody(final String queryString) throws UnsupportedEncodingException {
        final String decodedQueryString = URLDecoder.decode(queryString, StandardCharsets.UTF_8.name());

        this.query = new Query(decodedQueryString);
        this.messageBody = queryString;
    }
}
