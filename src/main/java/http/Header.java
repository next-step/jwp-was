package http;

import java.util.Objects;

public class Header {
    private HeaderName name;
    private String value;

    public Header(String headerLine) {
        String[] nameAndValue = headerLine.split(": ");

        if(nameAndValue.length != 2) {
            throw new IllegalArgumentException("헤더는 이름과 값이 콜론을 가지고 이루어져야만 합니다.");
        }

        this.name = HeaderName.findByName(nameAndValue[0]);
        this.value = nameAndValue[1];
    }

    public boolean exists(HeaderName headerName) {
        return name == headerName;
    }

    public String getName() {
        return this.name.getName();
    }

    public String getValue() {
        return this.value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Header header = (Header) o;
        return Objects.equals(name, header.name) &&
                Objects.equals(value, header.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, value);
    }

}
