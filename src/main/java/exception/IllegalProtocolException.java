package exception;

public class IllegalProtocolException extends RuntimeException {

	private static final String MESSAGE = "유효하지 않은 프로토콜입니다 : %s";

	public IllegalProtocolException(String[] tokens) {
		super(String.format(MESSAGE, String.join(" ", tokens)));
	}
}
