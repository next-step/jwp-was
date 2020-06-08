package http.response.sequelizer;

import http.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ResponseWriter {
    private static final Logger logger = LoggerFactory.getLogger(ResponseWriter.class);

    public static void write(OutputStream out, HttpResponse httpResponse) {
        DataOutputStream dos = new DataOutputStream(out);
        try {
            dos.writeBytes(ResponseLineSequelizer.sequelize(httpResponse));
            dos.writeBytes(ResponseCookieSequelizer.sequelize(httpResponse));
            dos.writeBytes(ResponseHeaderSequelizer.sequelize(httpResponse));
            dos.write(httpResponse.getBody(), 0, httpResponse.getBody().length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
