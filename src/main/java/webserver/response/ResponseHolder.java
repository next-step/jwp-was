package webserver.response;

import java.io.DataOutputStream;

public class ResponseHolder {

    private DataOutputStream dos;

    public ResponseHolder(DataOutputStream dos) {
        this.dos = dos;
    }

    public DataOutputStream getDos() {
        return dos;
    }

}
