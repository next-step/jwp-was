package webserver.response;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class ResponseBody {
    private final byte[] body;

    public ResponseBody(byte[] body) {
        this.body = body;
    }

    public static ResponseBody from(File file) throws IOException {
        return new ResponseBody(Files.readAllBytes(file.toPath()));
    }

    public int getContentLength() {
        return body.length;
    }

    public byte[] toBytes() {
        return body;
    }
}
