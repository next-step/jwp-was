package exception;

public class IllegalHttpVersionException extends RuntimeException {

	private static final String MESSAGE = "유효하지 않은 HTTP 버전입니다 : %s";

	public IllegalHttpVersionException(String[] tokens) {
		super(String.format(MESSAGE, String.join(" ", tokens)));
	}
}
