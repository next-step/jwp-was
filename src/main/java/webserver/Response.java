package webserver;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Response {
    private String path;
    private byte[] bytes;

    public Response(String path) throws IOException, URISyntaxException {
        this.path = path;

        String filePath = "./webapp/" + path;

        this.bytes = Files.readAllBytes(Paths.get(filePath));
    }

    public String getContentType() {
        return "text/html;charset=utf-8";
    }

    public String getPath() {
        return path;
    }

    public byte[] getBytes() {
        return bytes;
    }
}
