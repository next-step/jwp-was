package webserver.http.response;

import java.nio.charset.StandardCharsets;

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

    public String getContents() {
        return contents;
    }

    public int getContentLength() {
        return contents.length();
    }
}
