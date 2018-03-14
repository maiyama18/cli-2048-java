package tokyo.ymr27.cli2048java.view;

import tokyo.ymr27.cli2048java.model.GameField;
import tokyo.ymr27.cli2048java.utils.Direction;

import java.util.*;
import java.util.stream.Collectors;

public class GameView {
  private static final int CELL_WIDTH = 9;
  private static final int CELL_HEIGHT = 5;
  private static final String SQUARE_CODE = "\u25A0";
  private static final String BACKGROUND_COLOR_CODE = "\u001B[48;2;87;73;60m";
  private static final String BOLD_CODE = "\u001B[1m";
  private static final String RESET_CODE = "\u001B[0m";
  private static final Map<Integer, String> COLOR_CODES;

  static {
    COLOR_CODES = new HashMap<>();
    COLOR_CODES.put(null, "\u001B[38;2;105;103;90m");
    COLOR_CODES.put(2, "\u001B[38;2;238;228;218m");
    COLOR_CODES.put(4, "\u001B[38;2;237;224;200m");
    COLOR_CODES.put(8, "\u001B[38;2;242;177;121m");
    COLOR_CODES.put(16, "\u001B[38;2;245;149;99m");
    COLOR_CODES.put(32, "\u001B[38;2;246;124;95m");
    COLOR_CODES.put(64, "\u001B[38;2;246;93;60m");
    COLOR_CODES.put(128, "\u001B[38;2;237;207;114m");
    COLOR_CODES.put(256, "\u001B[38;2;237;190;100m");
    COLOR_CODES.put(512, "\u001B[38;2;226;180;90m");
    COLOR_CODES.put(1024, "\u001B[38;2;114;180;215m");
    COLOR_CODES.put(2048, "\u001B[38;2;1;120;186m");
  }

  private int size;

  public GameView(int size) {
    this.size = size;
  }

  public void showField(GameField gameField) {
    Integer[][] valueArray = gameField.getValueArray();

    printEmptyLine();
    for (int row = 0; row < size; row++) {
      Integer[] valueRow = valueArray[row];
      showValueRow(valueRow);
    }
    printEmptyLine();
  }

  public void showClearMessage() {
    System.out.println("Congratulations! You win!");
  }

  public void showLoseMessage() {
    System.out.println("Hahaha... You lose!");
  }

  public void showInputtedDirection(Direction direction, boolean isMoved) {
    String result = isMoved ? " o" : " x";
    System.out.println("=> " + direction + result);
  }

  private void showValueRow(Integer[] valueRow) {
    List<String> pixelArray = buildRowPixels(valueRow);
    for (String pixelRow : pixelArray) {
      printRowWithBackgroundColor(pixelRow);
    }
  }

  private List<String> buildRowPixels(Integer[] valueRow) {
    List<StringBuilder> pixelArray = new ArrayList<>();
    for (int i = 0; i < CELL_HEIGHT; i++) {
      pixelArray.add(new StringBuilder(""));
    }

    appendGutter(pixelArray, 2);
    for (Integer value : valueRow) {
      String valueStr = (value == null) ? "" : String.valueOf(value);
      String colorCode = COLOR_CODES.get(value);

      appendFrame(pixelArray, colorCode);
      insertValue(pixelArray, valueStr);
      appendGutter(pixelArray, 1);
    }
    appendGutter(pixelArray, 1);

    return pixelArray.stream()
            .map(StringBuilder::toString)
            .collect(Collectors.toList());
  }

  private void appendFrame(List<StringBuilder> pixelArray, String colorCode) {
    for (int row = 0; row < CELL_HEIGHT; row++) {
      StringBuilder pixelRow = pixelArray.get(row);

      // change color
      pixelRow.append(colorCode);

      appendSquares(pixelRow, 1);
      if (row == 0 || row == CELL_HEIGHT - 1) { // top or bottom
        appendSquares(pixelRow, CELL_WIDTH - 2);
      } else {
        appendSpaces(pixelRow, CELL_WIDTH - 2);
      }
      appendSquares(pixelRow, 1);
    }
  }
  private void appendGutter(List<StringBuilder> pixelArray, int width) {
    for (int row = 0; row < CELL_HEIGHT; row++) {
      StringBuilder pixelRow = pixelArray.get(row);
      appendSpaces(pixelRow, width);
    }
  }
  private void insertValue(List<StringBuilder> pixelArray, String valueStr) {
    StringBuilder pixelRow = pixelArray.get((CELL_HEIGHT - 1) / 2);

    int numEndSpaces = (CELL_WIDTH - 2 - valueStr.length()) / 2;
    int startPos = (pixelRow.length() - 1) - numEndSpaces - valueStr.length();

    pixelRow.replace(startPos, startPos + valueStr.length(), valueStr);
  }

  private void appendSquares(StringBuilder sb, int num) {
    for (int i = 0; i < num; i++) {
      sb.append(SQUARE_CODE);
    }
  }
  private void appendSpaces(StringBuilder sb, int num) {
    for (int i = 0; i < num; i++) {
      sb.append(" ");
    }
  }

  private void printEmptyLine() {
    int numSpaces = CELL_WIDTH * size + (size - 1) + 2 * 2;

    StringBuilder sbRow = new StringBuilder();
    for (int i = 0; i < numSpaces; i++) {
      sbRow.append(" ");
    }

    printRowWithBackgroundColor(sbRow.toString());
  }
  private void printRowWithBackgroundColor(String row) {
    System.out.print(BACKGROUND_COLOR_CODE);
    System.out.print(BOLD_CODE);
    System.out.print(row);
    System.out.print(RESET_CODE);
    System.out.println();
  }
}
