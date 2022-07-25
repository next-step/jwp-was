package webserver.exception;

public class InvalidPasswordException extends RuntimeException {

	private static final String MESSAGE = "유효하지 않는 비밀번호 입니다.";

	public InvalidPasswordException() {
		super(MESSAGE);
	}
}
