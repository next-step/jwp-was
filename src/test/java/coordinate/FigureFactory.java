package coordinate;

import java.util.List;

public class FigureFactory {
    static Figure getInstance(List<Point> points) {
        FigureType figureType = FigureType.of(points.size());
        return figureType.create(points);
    }
}
