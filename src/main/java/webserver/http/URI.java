package webserver.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class URI {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final String URL_DECODE_ERROR_MESSAGE = "Url 디코딩에 실패 하였습니다.";

    private String path;
    private String url;
    private QueryString queryString;

    private URI(String url, QueryString queryString) {
        this.path = parsePath(url);
        this.url = url;
        this.queryString = queryString;
    }

    public static URI parse(String uriString) {
        String decodedUrl = getDecodedUrl(uriString);
        return new URI(decodedUrl, QueryString.parse(decodedUrl));
    }

    public String getPath() {
        return this.path;
    }

    public QueryString getQueryString() {
        return this.queryString;
    }

    public String getUrl() {
        return this.url;
    }

    private String parsePath(String url) {
        int lastIndex = url.contains("?") ? url.indexOf("?") : url.length();
        return url.substring(0, lastIndex);
    }

    private static String getDecodedUrl(String url){
        try {
            return URLDecoder.decode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.warn(URL_DECODE_ERROR_MESSAGE);
        }

        return url;
    }

    @Override
    public String toString() {
        return url;
    }
}
