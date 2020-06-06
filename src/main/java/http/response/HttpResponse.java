package http.response;

import http.Headers;
import java.io.IOException;

public interface HttpResponse {

    byte[] getBody() throws IOException;

    Headers getHeaders();

    String getStatusMessage();
}
