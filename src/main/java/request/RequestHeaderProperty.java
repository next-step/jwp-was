/*
 * Copyright (c) 2019 LINE Corporation. All rights reserved.
 * LINE Corporation PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package request;

import header.setter.HeaderSetter;
import header.setter.StringSetter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by youngjae.havi on 2019-08-02
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestHeaderProperty {

    String USE_DEFAULT_NAME = "";

    /**
     * Request Header에서 전달되는 파라미터의 명과 {@link HttpRequest}의 필드값을 맵핑하기 위해 사용
     * @return Request Header에서 전달되는 파라미터의 정식 명칭
     */
    String value() default USE_DEFAULT_NAME;

    /**
     * 각 header 타입에 맞는 객체 형변환을 위한 converter class
     * @return
     */
    Class<? extends HeaderSetter> converter() default StringSetter.class;
}
