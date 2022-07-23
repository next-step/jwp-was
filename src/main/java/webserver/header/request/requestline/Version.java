package webserver.header.request.requestline;

import webserver.header.exception.InvalidVersionException;

public class Version {
    public static final String VERSION_DELIMITER = "\\.";
    private final String number;

    public Version(String number) {
        validate(number);
        this.number = number;
    }

    @Override
    public String toString() {
        return number;
    }

    private void validate(String number) {
        String[] elements = number.split(VERSION_DELIMITER);

        if (elements.length != 2) {
            throw new InvalidVersionException("유효하지 않은 버전 입니다. x.x 형식으로 버전을 명시하세요.");
        }

        if (isNotDigit(elements[0]) || isNotDigit(elements[1])) {
            throw new InvalidVersionException("유효하지 않은 버전 입니다. 버전은 숫자로 구성되어야 합니다.");
        }
    }

    private boolean isNotDigit(String digit) {
        return !digit.chars().allMatch(Character::isDigit);
    }
}
