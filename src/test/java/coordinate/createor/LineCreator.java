package coordinate.createor;

import coordinate.Figure;
import coordinate.FigureCreator;
import coordinate.Line;
import coordinate.Point;

import java.util.List;

public class LineCreator implements FigureCreator {
    @Override
    public Figure create(List<Point> points) {
        return new Line(points);
    }
}
