package coordinate.createor;

import coordinate.Figure;
import coordinate.FigureCreator;
import coordinate.Point;
import coordinate.Triangle;

import java.util.List;

public class TriangleCreator implements FigureCreator {
    @Override
    public Figure create(List<Point> points) {
        return new Triangle(points);
    }
}
