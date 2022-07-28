package webserver.domain;

/**
 * 엔티티(도메인) 제공자 인터페이스
 *
 * @param <T> 엔티티 타입
 */
public interface EntitySupplier<T> {

    /**
     * 제네릭 타입으로 정의된 엔티티를 반환한다.
     *
     * @return 엔티티
     */
    T supply();
}
