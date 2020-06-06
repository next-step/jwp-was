package webserver.reader;

import java.io.IOException;
import java.io.InputStream;

public interface RequestReader {
    String readStream(final InputStream inputStream) throws IOException;
}
