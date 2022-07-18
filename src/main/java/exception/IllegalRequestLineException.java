package exception;

public class IllegalRequestLineException extends RuntimeException {

	private static final String MESSAGE = "유효하지 않은 HTTP 요청입니다 : %s";

	public IllegalRequestLineException(String[] tokens) {
		super(String.format(MESSAGE, String.join(" ", tokens)));
	}
}
