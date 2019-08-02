package coordinate;

import java.util.List;

public class FigureFactory {

  static Figure getInstance(final List<Point> points) {
    return FigureType.from(points);
  }

}
