/*
 * Copyright (c) 2019 LINE Corporation. All rights reserved.
 * LINE Corporation PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package webserver;

import static request.HttpMethod.GET;

/**
 * Created by youngjae.havi on 2019-08-02
 */
public class Controller {

    @Request(method = GET, path = {"", "/"})
    public byte[] main() {
        return null;
    }

    @Request(method = GET, path = "/index.html")
    public byte[] index() {
        return null;
    }

    @Request(method = GET, path = "/user/form.html")
    public byte[] userForm() {
        return null;
    }

    @Request(method = GET, path = "/create")
    public byte[] userCreate() {
        return null;
    }
}
