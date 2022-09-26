package webserver.servlet;

import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public class AbstractClass {
	// 응답과 요청 상태코드 맞추기
	// 인증과 같은 필터
	// 로깅
	// 캐싱
	// 압축
	// 인코딩
	// forward 처리와 같은 공통 로직
	// - 서블릿을 찾는다.
	// - 서블릿을 실행한다.
	// 예외 처리

	public void forward(HttpRequest request, HttpResponse response) {
		// response.addHeader("Content-Length", String.valueOf(content.length));
		//
		// String httpHeader = response.toEncoded();
		// response.getWriter().writeBytes(httpHeader);
		//
		// response.getWriter().write(content, 0, content.length);
		// response.getWriter().flush();
	}
}