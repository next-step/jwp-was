package exception;

public class IllegalHttpMethodException extends RuntimeException {

	private static final String MESSAGE = "유효하지 않은 HTTP 메서드입니다 : %s";

	public IllegalHttpMethodException(String method) {
		super(String.format(MESSAGE, method));
	}
}
