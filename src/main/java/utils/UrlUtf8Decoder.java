package utils;

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
            throw new RuntimeException("지원하지 않는 Encoding 타입. Type : [" + EncodeType + "]");
        }
        return result;
    }
}
