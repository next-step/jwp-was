package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class DecoderUtils {

    private static final Logger logger = LoggerFactory.getLogger(DecoderUtils.class);

    private static final String UTF_8 = "UTF-8";

    public static String decode(String input) {
        try {
            return URLDecoder.decode(String.valueOf(input), UTF_8);
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage());
        }
        return input;
    }
}
