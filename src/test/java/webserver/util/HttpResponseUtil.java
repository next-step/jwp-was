package webserver.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class HttpResponseUtil {
    private HttpResponseUtil() {
    }

    public static String getHttpResponse(byte[] bytes) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        String result = null;

        try {
            byteArrayOutputStream.write(bytes);
            result = byteArrayOutputStream.toString("UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
