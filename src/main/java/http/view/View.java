package http.view;

import http.Headers;
import http.HttpStatus;
import java.io.IOException;

public interface View {
    HttpStatus getHttpStatus();
    byte[] getBody() throws IOException;
    Headers getHeaders();
}
