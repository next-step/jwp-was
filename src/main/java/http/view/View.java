package http.view;

import http.HttpResponse;

/**
 * Created by iltaek on 2020/06/12 Blog : http://blog.iltaek.me Github : http://github.com/iltaek
 */
public interface View {

    void render(HttpResponse response);
}
