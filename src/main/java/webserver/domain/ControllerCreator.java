package webserver.domain;

/**
 * 웹 기본 컨트롤러 구조
 */
public interface ControllerCreator {
    void doPost();
    void doGet();
}
