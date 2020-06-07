package http.view;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import utils.HttpUtils;

public abstract class FileResourceView implements View {

    @Override
    public void response(OutputStream out) throws IOException {
        BodyFile file = getBodyFile();

        DataOutputStream dos = new DataOutputStream(out);
        dos.writeBytes("Content-Type: " + HttpUtils.getMimeType(file.fileName) + "\r\n");
        dos.writeBytes("Content-Length: " + file.bytes.length + "\r\n");

        dos.writeBytes("\r\n");
        dos.write(file.bytes, 0, file.bytes.length);
    }

    protected abstract BodyFile getBodyFile() throws IOException;

     static class BodyFile{
         private String fileName;
         private byte[] bytes;

         public BodyFile(String fileName, byte[] bytes) {
             this.fileName = fileName;
             this.bytes = bytes;
         }
     }
}
