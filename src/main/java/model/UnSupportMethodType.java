package model;

public class UnSupportMethodType extends RuntimeException {

    public UnSupportMethodType() {
        super("지원하지 않는 메소드 타입입니다.");
    }
}
