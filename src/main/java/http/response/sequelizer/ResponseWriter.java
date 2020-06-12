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
            dos.writeBytes(ResponseSequelizer.RESPONSE_LINE.sequelize(httpResponse));
            dos.writeBytes(ResponseSequelizer.COOKIE.sequelize(httpResponse));
            dos.writeBytes(ResponseSequelizer.HEADER.sequelize(httpResponse));
            dos.write(httpResponse.getBody(), 0, httpResponse.getBody().length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
