package coordinate;

import java.util.List;

public class LineCreator implements FigureCreator {

    @Override
    public Figure create(List<Point> points) {
        return new Line(points);
    }
}
