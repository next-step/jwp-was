package model.response;

import java.io.DataOutputStream;
import java.io.IOException;

public class ResponseBody {
    private byte[] body;

    public ResponseBody(byte[] body) {
        this.body = body;
    }

    public int getLength() {
        return this.body.length;
    }

    public void writeOutput(DataOutputStream dos) throws IOException {
        dos.write(this.body, 0, this.body.length);
        dos.flush();
    }
}
