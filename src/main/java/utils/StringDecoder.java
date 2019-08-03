package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLDecoder;

public class StringDecoder {
    private static final Logger logger = LoggerFactory.getLogger(StringDecoder.class);
    private static final String URL_DECODE_ERROR_MESSAGE = "Url 디코딩에 실패 하였습니다.";

    public static String decode(String url){
        try {
            return URLDecoder.decode(url, "UTF-8");
        } catch (Exception e) {
            logger.warn(URL_DECODE_ERROR_MESSAGE);
        }

        return url;
    }
}
