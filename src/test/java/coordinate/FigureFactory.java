package coordinate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class FigureFactory {
    private static final Map<Integer, FigureCreator> map;

    static {
        map = new HashMap<>();
        FigureFactory.map.put(2, new LineCreator());
        FigureFactory.map.put(3, new TriangleCreator());
        FigureFactory.map.put(4, new RectangleCreator());
    }

    static Figure getInstance(List<Point> points) {
        int size = points.size();
        if (!FigureFactory.map.containsKey(size)) {
            throw new IllegalArgumentException("유효하지 않은 도형입니다.");
        }
        return FigureFactory.map.get(size).create(points);
    }
}
