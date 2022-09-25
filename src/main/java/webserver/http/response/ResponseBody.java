package webserver.http.response;

import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;

public class ResponseBody {

    private final byte[] contents;

    private ResponseBody(byte[] contents) {
        this.contents = contents;
    }

    public static ResponseBody empty() {
        return new ResponseBody(new byte[0]);
    }

    public static ResponseBody from(String filePath) {
        if (filePath == null || filePath.length() == 0) {
            return ResponseBody.empty();
        }
        byte[] responsePage = toFile(filePath);
        return new ResponseBody(responsePage);
    }

    public static ResponseBody from(byte[] contents) {
        if (contents == null || contents.length == 0) {
            return ResponseBody.empty();
        }
        return new ResponseBody(contents);
    }

    private static byte[] toFile(String filePath) {
        try {
            return FileIoUtils.loadFileFromClasspath(filePath);
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public int getContentsLength() {
        return contents.length;
    }

    public byte[] getContents() {
        return contents;
    }
}
