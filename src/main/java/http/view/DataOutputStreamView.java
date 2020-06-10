package http.view;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by iltaek on 2020/06/10 Blog : http://blog.iltaek.me Github : http://github.com/iltaek
 */
public class DataOutputStreamView implements View {

    private static final Logger logger = LoggerFactory.getLogger(DataOutputStreamView.class);

    private DataOutputStream dos;

    public DataOutputStreamView(OutputStream out) {
        this.dos = new DataOutputStream(out);
    }

    @Override
    public void render(String header, byte[] body) {
        renderHeader(header);
        renderBody(body);
    }

    private void renderHeader(String header) {
        try {
            dos.writeBytes(header);
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
