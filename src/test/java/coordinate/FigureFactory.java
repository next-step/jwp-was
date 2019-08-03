package coordinate;

import java.util.List;

public class FigureFactory {
    static Figure getInstance(List<Point> points) {
        return FigureCreators.get(points.size())
                .map(f -> f.create(points))
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 도형입니다."));
    }
}
