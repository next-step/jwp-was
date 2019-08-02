package coordinate;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public enum FigureType {
  LINE(Line.SIZE, Line::new),
  TRIANGLE(Triangle.SIZE, Triangle::new),
  RECTANGLE(Rectangle.SIZE, Rectangle::new);

  private static Map<Integer, FigureCreator> figureCreators;
  private int pointCount;
  private FigureCreator figureCreator;

  FigureType(int pointCount, FigureCreator figureCreator) {
    this.pointCount = pointCount;
    this.figureCreator = figureCreator;
  }

  static {
    figureCreators = Arrays.stream(FigureType.values())
        .collect(Collectors
            .toMap(figure -> figure.pointCount, figure -> figure.figureCreator));
  }

  public static Figure from(List<Point> points) {
    return figureCreators.keySet().stream()
        .filter(key -> key == points.size())
        .map(key -> figureCreators.get(key))
        .findFirst()
        .map(figureCreator -> figureCreator.create(points))
        .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 도형입니다."));
  }

}
