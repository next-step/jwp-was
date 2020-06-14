package http;

import javax.annotation.Nonnull;

public class ResponseBody {
    // TODO byte? string?
    private byte[] messageBodyByteArray;

    public ResponseBody(byte[] messageBodyByteArray) {
        this.messageBodyByteArray = messageBodyByteArray;
    }

    @Nonnull
    public static ResponseBody makeEmptyResponseBody() {
        return new EmptyResponseBody();
    }

        static class EmptyResponseBody extends ResponseBody {
        private EmptyResponseBody() {
            super(new byte[0]);
        }
    }

    public byte[] getMessageBodyByteArray() {
        return messageBodyByteArray;
    }
}
