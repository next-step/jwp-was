package model.response;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class ResponseBody {

    private byte[] body;

    public ResponseBody(byte[] body) {
        this.body = body;
    }

    public static ResponseBody empty() {
        return new ResponseBody(new byte[0]);
    }

    public int getLength() {
        return body.length;
    }

    public void writeOutput(DataOutputStream dos) throws IOException {
        if (hasBody()) {
            dos.write(body, 0, getLength());
        }
    }

    private boolean hasBody() {
        return body != null && body.length > 0;
    }

    @Override
    public String toString() {
        return "ResponseBody{" +
                "body=" + Arrays.toString(body) +
                '}';
    }
}
