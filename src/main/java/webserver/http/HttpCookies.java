package webserver.http;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import utils.StringUtils;
import webserver.exceptions.CookieParsingException;

public class HttpCookies {

	public static final String COOKIES_SPLIT_SIGN = ";";

	public static final String COOKIE_SPLIT_SIGN = "=";

	public static final int COOKIE_SPLIT_LIMIT = 2;

	private Map<String, HttpCookie> cookies;

	private HttpCookies(Map<String, HttpCookie> cookies) {
		this.cookies = cookies;
	}

	public static HttpCookies of(String cookieValues) {
		Map<String, HttpCookie> cookies = getCookiesFromCookieSplitValues(getCookieSplitValues(cookieValues));

		return new HttpCookies(cookies);
	}

	private static List<String> getCookieSplitValues(String cookieValues) {
		return Optional.ofNullable(cookieValues)
				.map(values -> values.split(COOKIES_SPLIT_SIGN))
				.map(Arrays::asList)
				.orElse(Collections.emptyList());
	}

	private static Map<String, HttpCookie> getCookiesFromCookieSplitValues(List<String> cookieSplitValues) {
		return Optional.ofNullable(cookieSplitValues)
				.orElse(Collections.emptyList())
				.stream()
				.filter(value -> !StringUtils.isEmpty(value) && !StringUtils.isEmpty(value.trim()))
				.map(HttpCookies::parseHttpCookie)
				.collect(Collectors.toMap(httpCookie -> httpCookie.getName(), Function.identity(), (c1, c2) -> c1));
	}

	private static HttpCookie parseHttpCookie(String cookieValue) {
		String[] cookieNameAndValue = cookieValue.split(COOKIE_SPLIT_SIGN, COOKIE_SPLIT_LIMIT);

		if (cookieNameAndValue.length != COOKIE_SPLIT_LIMIT) {
			throw new CookieParsingException(CookieParsingException.COOKIE_SPLIT_INVALID_LENGTH);
		}

		return new HttpCookie(cookieNameAndValue[0].trim(), cookieNameAndValue[1].trim());
	}

	public HttpCookie getCookie(String cookieName) {
		return cookies.get(cookieName);
	}
}
