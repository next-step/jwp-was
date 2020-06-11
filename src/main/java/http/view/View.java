package http.view;

/**
 * Created by iltaek on 2020/06/10 Blog : http://blog.iltaek.me Github : http://github.com/iltaek
 */
public interface View {

    void render(String header, byte[] body);
}
