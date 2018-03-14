package tokyo.ymr27.cli2048java.model.parts;

import tokyo.ymr27.cli2048java.utils.Direction;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Traversal {
  private final List<Integer> rowIndices;
  private final List<Integer> colIndices;

  public Traversal(Direction direction, int size) {
    rowIndices = IntStream.range(0, size)
            .boxed()
            .collect(Collectors.toList());
    colIndices = IntStream.range(0, size)
            .boxed()
            .collect(Collectors.toList());

    if (direction == Direction.RIGHT) {
      Collections.reverse(colIndices);
    } else if (direction == Direction.DOWN) {
      Collections.reverse(rowIndices);
    }
  }

  public List<Integer> getRowIndices() {
    return rowIndices;
  }
  public List<Integer> getColIndices() {
    return colIndices;
  }
}
