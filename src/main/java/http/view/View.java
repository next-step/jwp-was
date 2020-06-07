package http.view;

import http.Headers;
import http.HttpStatus;
import java.io.IOException;
import java.io.OutputStream;

public interface View {
    HttpStatus getHttpStatus();
    void response(OutputStream out) throws IOException;
}
