package utils;

import org.apache.tika.Tika;

public class HttpUtils {

    public static String getMimeType(String fileName) {
        return new Tika().detect(fileName);
    }
}
