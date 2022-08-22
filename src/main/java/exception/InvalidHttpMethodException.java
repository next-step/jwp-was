package exception;

public class InvalidHttpMethodException extends RuntimeException{

    public InvalidHttpMethodException() {
        super("유효하지 않은 Http Method 입니다.");
    }
}
