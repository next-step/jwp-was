package http.response;

import java.io.IOException;

public interface HttpResponse {

    byte[] getBody() throws IOException;
}
