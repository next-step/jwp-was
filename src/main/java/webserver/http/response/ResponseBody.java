package webserver.http.response;

import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;

public class ResponseBody {

    private final String contents;

    private ResponseBody(String contents) {
        this.contents = contents;
    }

    public static ResponseBody empty() {
        return new ResponseBody("");
    }

    public static ResponseBody from(String response) {
        if (response == null || response.length() == 0) {
            return ResponseBody.empty();
        }
        return new ResponseBody(response);
    }

    public byte[] toFile() {
        try {
            return FileIoUtils.loadFileFromClasspath(contents);
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public int getFileLength() {
        return toFile().length;
    }

    public String getContents() {
        return contents;
    }
}
