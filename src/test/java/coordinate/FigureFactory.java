package coordinate;

import java.util.List;

public class FigureFactory {
    static Figure getInstance(List<Point> points) {
        return FigureConstructor
                .getFigureConstructor(points);
    }
}
