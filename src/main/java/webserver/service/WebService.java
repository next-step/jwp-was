package webserver.service;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;

public interface WebService {
    public void process(OutputStream outputStream, Object...obj) throws IOException, URISyntaxException;
}
