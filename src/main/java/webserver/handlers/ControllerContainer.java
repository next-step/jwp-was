package webserver.handlers;

import webserver.domain.RequestLine;
import webserver.ui.Controller;

/**
 * 컨트롤러 관리자
 */
public interface ControllerContainer {

    /**
     * 요청 정보를 지원하는 컨트롤러인지 여부를 반환한다.
     *
     * @param requestLine 요청 정보
     * @return 지원 여부
     */
    boolean support(RequestLine requestLine);

    /**
     * 요청 정보를 지원하는 컨트롤러를 찾아 반환한다.
     *
     * @param requestLine 요청 정보
     * @return 컨트롤러
     */
    Controller findController(RequestLine requestLine);

    /**
     * 컨트롤러를 컨테이너에 추가하며, 메서드 체이닝을 지원한다.
     */
    ControllerContainer addController(Controller controller);
}
