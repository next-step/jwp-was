package webserver.exception;

public class RegisterDuplicateException  extends RuntimeException {

	private static final String MESSAGE = "이미 등록한 사용자 입니다.";

	public RegisterDuplicateException() {
		super(MESSAGE);
	}
}
