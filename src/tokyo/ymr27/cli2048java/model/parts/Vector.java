package tokyo.ymr27.cli2048java.model.parts;

import tokyo.ymr27.cli2048java.utils.Direction;

import java.util.Objects;

public class Vector {
  private final int row;
  private final int col;

  public Vector(int row, int col) {
    this.row = row;
    this.col = col;
  }

  public static Vector unit(Direction direction) {
    switch (direction) {
      case UP:
        return new Vector(-1, 0);
      case DOWN:
        return new Vector(1, 0);
      case LEFT:
        return new Vector(0, -1);
      case RIGHT:
        return new Vector(0, 1);
      default:
        return new Vector(0, 0);
    }
  }

  public int getRow() {
    return row;
  }

  public int getCol() {
    return col;
  }

  public Vector plus(Vector other) {
    return new Vector(this.row + other.getRow(), this.col + other.getCol());
  }

  public Vector minus(Vector other) {
    return new Vector(this.row - other.getRow(), this.col - other.getCol());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Vector vector = (Vector) o;
    return row == vector.row &&
            col == vector.col;
  }

  @Override
  public int hashCode() {
    return Objects.hash(row, col);
  }
}
