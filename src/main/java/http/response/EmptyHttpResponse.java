package http.response;

import http.HttpResponse;
import java.io.IOException;

public class EmptyHttpResponse implements HttpResponse {

    @Override
    public byte[] getBody() throws IOException {
        return new byte[0];
    }
}
