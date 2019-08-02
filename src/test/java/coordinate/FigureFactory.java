package coordinate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FigureFactory {

    private static Map<Integer, FigureCreator> creators = new HashMap<>();

    static {
        creators.put(2, Line::new);
        creators.put(3, Triangle::new);
        creators.put(4, Rectangle::new);
    }

    static Figure getInstance(List<Point> points) {
        int size = points.size();
        if (!creators.containsKey(size)) {
            throw new IllegalArgumentException("유효하지 않은 도형입니다.");
        }
        return creators.get(size).create(points);
    }
}
