package http;

import java.io.IOException;

public interface HttpResponse {

    byte[] getBody() throws IOException;
}
