package exception;

public class IllegalHttpProtocolException extends RuntimeException {

	private static final String MESSAGE = "유효하지 않은 HTTP 프로토콜입니다 : %s";

	public IllegalHttpProtocolException(String[] tokens) {
		super(String.format(MESSAGE, String.join(" ", tokens)));
	}
}
