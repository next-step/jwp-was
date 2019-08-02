package coordinate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FigureFactory {

  private static final Map<Integer, FigureCreator> FIGURE_CREATORS = new HashMap<>();

  static {
    FIGURE_CREATORS.put(2, Line::new);
    FIGURE_CREATORS.put(3, Triangle::new);
    FIGURE_CREATORS.put(4, Rectangle::new);
  }

  static Figure getInstance(final List<Point> points) {
    if (!hasFigure(points)) {
      throw new IllegalArgumentException("유효하지 않은 도형입니다.");
    }
    return FIGURE_CREATORS.get(points.size()).create(points);
  }

    private static boolean hasFigure(List<Point> points) {
        return FIGURE_CREATORS.containsKey(points.size());
    }
}
