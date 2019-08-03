/*
 * Copyright (c) 2019 LINE Corporation. All rights reserved.
 * LINE Corporation PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package handler;

import request.RequestHeader;
import response.Response;

/**
 * 서포트하는 request에 매핑하기 위한 전략 인터페이스
 */
public interface RequestStrategy {
    /**
     * 해당 response 객체 반환
     * @param requestHeader
     * @return
     */
    Response request(RequestHeader requestHeader);

    /**
     * 어떤 전략 인터페이스를 선택할건지 체크하는 메서드
     * @param requestHeader
     * @return
     */
    boolean isSupport(RequestHeader requestHeader);
}
