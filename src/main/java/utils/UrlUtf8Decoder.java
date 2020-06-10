package utils;

import webserver.exceptions.ErrorMessage;
import webserver.exceptions.WebServerException;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class UrlUtf8Decoder {
    public static String decode(String s) {
        final String EncodeType = "UTF-8";
        String result = null;
        try {
            result = URLDecoder.decode(s, EncodeType);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new WebServerException(ErrorMessage.UNSSUPORTED_ENCODING);
        }
        return result;
    }
}
