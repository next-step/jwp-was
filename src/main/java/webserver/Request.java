/*
 * Copyright (c) 2019 LINE Corporation. All rights reserved.
 * LINE Corporation PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package webserver;

import request.RequestHeader;
import response.Response;

/**
 * Created by youngjae.havi on 2019-08-02
 */
public interface Request {
    Response request(RequestHeader requestHeader);
}
