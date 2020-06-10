package utils;


import org.apache.logging.log4j.util.Strings;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class UrlUtf8Decoder {
    public static String decode(String s) {
        final String EncodeType = "UTF-8";
        String result = Strings.EMPTY;
        try {
            result = URLDecoder.decode(s, EncodeType);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new webserver.exceptions.UnsupportedEncodingException(EncodeType);
        }
        return result;
    }
}
