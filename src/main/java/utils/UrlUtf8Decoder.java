package utils;

import webserver.exceptions.UncheckedUnsupportedEncodingException;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class UrlUtf8Decoder {
    public static String decode(String s) {
        final String EncodeType = "UTF-8";
        try {
            return URLDecoder.decode(s, EncodeType);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new UncheckedUnsupportedEncodingException(EncodeType);
        }
    }
}
