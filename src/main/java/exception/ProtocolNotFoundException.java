package exception;

public class ProtocolNotFoundException extends RuntimeException {

    public ProtocolNotFoundException() {
        super("protocol not found !!", new IllegalArgumentException());
    }
}
