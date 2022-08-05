package webserver.http.domain.cookie;

import java.util.Date;
import java.util.Objects;

import static webserver.http.domain.cookie.OptionalCookieAttributes.DOMAIN;
import static webserver.http.domain.cookie.OptionalCookieAttributes.EXPIRES;
import static webserver.http.domain.cookie.OptionalCookieAttributes.HTTP_ONLY;
import static webserver.http.domain.cookie.OptionalCookieAttributes.MAX_AGE;
import static webserver.http.domain.cookie.OptionalCookieAttributes.PATH;
import static webserver.http.domain.cookie.OptionalCookieAttributes.SAME_SITE;
import static webserver.http.domain.cookie.OptionalCookieAttributes.SECURE;

public class Cookie {
    private final String name;
    private final String value;
    private final OptionalCookieAttributes optionalCookieAttributes;

    public Cookie(String name, String value) {
        this.name = name;
        this.value = value;
        this.optionalCookieAttributes = new OptionalCookieAttributes();
    }

    public Cookie(String name, String value, OptionalCookieAttributes optionalCookieAttributes) {
        this.name = name;
        this.value = value;
        this.optionalCookieAttributes = optionalCookieAttributes;
    }

    public static Cookie of(String name, boolean value) {
        return new Cookie(name, String.valueOf(value));
    }

    public static Cookie of(String name, String value) {
        return new Cookie(name, value);
    }

    public boolean equalsValue(String value) {
        return Objects.equals(value, this.value);
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public String getPath() {
        return optionalCookieAttributes.getPath();
    }

    public void setPath(String path) {
        optionalCookieAttributes.setPath(path);
    }

    public Integer getMaxAge() {
        return optionalCookieAttributes.getMaxAge();
    }

    public void setMaxAge(Integer maxAge) {
        optionalCookieAttributes.setMaxAge(maxAge);
    }

    public Date getExpires() {
        return optionalCookieAttributes.getExpires();
    }

    public String getDomain() {
        return optionalCookieAttributes.getDomain();
    }

    public void setDomain(String domain) {
        optionalCookieAttributes.setDomain(domain);
    }

    public String getSameSite() {
        return optionalCookieAttributes.getSameSite();
    }

    public boolean isHttpOnly() {
        return optionalCookieAttributes.isHttpOnly();
    }

    public boolean isSecure() {
        return optionalCookieAttributes.isSecure();
    }

    public static CookieBuilder builder(String name, String value) {
        return new CookieBuilder(name, value);
    }

    public static class CookieBuilder {
        private String name;
        private String value;

        private OptionalCookieAttributes optionalCookieAttributes;

        public CookieBuilder(String name, String value) {
            this.name = name;
            this.value = value;
            this.optionalCookieAttributes = new OptionalCookieAttributes();
        }

        public CookieBuilder path(String path) {
            optionalCookieAttributes.setPath(path);
            return this;
        }

        public CookieBuilder domain(String domain) {
            optionalCookieAttributes.setDomain(domain);
            return this;
        }

        public CookieBuilder expires(Date expires) {
            optionalCookieAttributes.setExpires(expires);
            return this;
        }

        public CookieBuilder maxAge(Integer maxAge) {
            optionalCookieAttributes.setMaxAge(maxAge);
            return this;
        }

        public CookieBuilder sameSite(String sameSite) {
            optionalCookieAttributes.setSameSite(sameSite);
            return this;
        }

        public CookieBuilder httpOnly(boolean httpOnly) {
            optionalCookieAttributes.setHttpOnly(httpOnly);
            return this;
        }

        public CookieBuilder secure(boolean secure) {
            optionalCookieAttributes.setSecure(secure);
            return this;
        }

        public Cookie build() {
            return new Cookie(name, value, optionalCookieAttributes);
        }

    }

    public String getAsHeaderValue() {
        StringBuffer stringBuffer = new StringBuffer(String.format("%s=%s", name, value));

        appendOptionalAttributeIfPresent(stringBuffer, PATH, getPath());
        appendOptionalAttributeIfPresent(stringBuffer, MAX_AGE, getMaxAge());
        appendOptionalAttributeIfPresent(stringBuffer, EXPIRES, getExpires());
        appendOptionalAttributeIfPresent(stringBuffer, DOMAIN, getDomain());
        appendOptionalAttributeIfPresent(stringBuffer, SAME_SITE, getSameSite());
        appendOptionalAttributeIfActive(stringBuffer, HTTP_ONLY, isHttpOnly());
        appendOptionalAttributeIfActive(stringBuffer, SECURE, isSecure());
        return stringBuffer.toString();
    }

    private void appendOptionalAttributeIfPresent(StringBuffer stringBuffer, String name, Object value) {
        if (Objects.isNull(value)) {
            return;
        }
        stringBuffer.append("; ");
        stringBuffer.append(name);
        stringBuffer.append("=");
        stringBuffer.append(value);
    }

    private void appendOptionalAttributeIfActive(StringBuffer stringBuffer, String name, boolean isActive) {
        if (isActive) {
            stringBuffer.append("; ");
            stringBuffer.append(name);
        }
    }
}
