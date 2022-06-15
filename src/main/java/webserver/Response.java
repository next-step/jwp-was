package webserver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Response {
    private String path;

    public Response(String path) {
        this.path = path;
    }

    public String getContentType() {
        return "text/html;charset=utf-8";
    }

    public String getPath() {
        return path;
    }

    public byte[] getBytes() throws IOException {
        return Files.readAllBytes(Paths.get("./webapp/" + path));
    }
}
