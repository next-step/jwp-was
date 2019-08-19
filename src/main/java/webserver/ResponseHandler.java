package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.response.HttpResponse;

import java.io.DataOutputStream;
import java.io.OutputStream;

public class ResponseHandler {
    private static final Logger logger = LoggerFactory.getLogger(ResponseHandler.class);

    public static void response(OutputStream out, HttpResponse response) {
        try {
            DataOutputStream doc = new DataOutputStream(out);
            doc.writeBytes(response.getResponseHeader());
            doc.write(response.getResponseBody(), 0, response.getResponseBodyLength());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}