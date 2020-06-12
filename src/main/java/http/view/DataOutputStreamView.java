package http.view;

import http.HttpResponse;
import http.controller.CreateUserController;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by iltaek on 2020/06/12 Blog : http://blog.iltaek.me Github : http://github.com/iltaek
 */
public class DataOutputStreamView implements View {

    private static final Logger logger = LoggerFactory.getLogger(DataOutputStreamView.class);

    private final DataOutputStream dos;

    public DataOutputStreamView(OutputStream out) {
        this.dos = new DataOutputStream(out);
    }

    @Override
    public void render(HttpResponse response) {
        renderHeader(response.getHeaderString());
        renderBody(response.getBody());
    }

    private void renderHeader(String headerString) {
        try {
            dos.writeBytes(headerString);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void renderBody(byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
