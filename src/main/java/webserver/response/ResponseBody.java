package webserver.response;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ResponseBody {
    private final String body;

    public ResponseBody(String body) {
        this.body = body;
    }

    public static ResponseBody from(Path path) throws IOException {
        return new ResponseBody(Files.readString(path));
    }

    public int getBytesLength() {
        return body.getBytes().length;
    }

    @Override
    public String toString() {
        return body;
    }
}
