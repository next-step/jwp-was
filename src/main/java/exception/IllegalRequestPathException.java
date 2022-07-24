package exception;

public class IllegalRequestPathException extends RuntimeException {

	private static final String MESSAGE = "유효하지 않은 요청 path 입니다 : %s";

	public IllegalRequestPathException(String path) {
		super(String.format(MESSAGE, path));
	}
}
