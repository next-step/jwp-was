package header;

import response.HeaderResponse;

/**
 * Created by youngjae.havi on 2019-08-04
 */
public class ContentLength implements HeaderResponse {
    private int contentLength;

    public ContentLength(int contentLength) {
        this.contentLength = contentLength;
    }

    @Override
    public String key() {
        return "Content-Length";
    }

    @Override
    public String valueAll() {
        return String.valueOf(contentLength);
    }
}
