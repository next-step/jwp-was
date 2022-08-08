package utils;


import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class WatcherOutputStream extends DataOutputStream {
    private byte[] data = null;
    private ByteArrayOutputStream bos;

    public WatcherOutputStream(OutputStream delegateStream) {
        super(delegateStream);
        this.bos = new ByteArrayOutputStream();
    }

    public void write(byte[] b) throws IOException {
        appendByteArray(b);
        super.write(b);
    }

    public byte[] getData() {
        return data;
    }

    public void write(String value) throws IOException {
        byte[] bytes = value.getBytes();
        appendByteArray(bytes);
        super.write(bytes);
    }

    private void appendByteArray(byte[] b) throws IOException {
        bos.write(b);
        data = bos.toByteArray();
    }
}
