package tokyo.ymr27.cli2048java.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class InputManager {
  private static final Map<Character, Direction> VALID_KEYMAPS;
  static {
    VALID_KEYMAPS = new HashMap<>();

    // wasd
    VALID_KEYMAPS.put('w', Direction.UP);
    VALID_KEYMAPS.put('s', Direction.DOWN);
    VALID_KEYMAPS.put('a', Direction.LEFT);
    VALID_KEYMAPS.put('d', Direction.RIGHT);

    // vim
    VALID_KEYMAPS.put('k', Direction.UP);
    VALID_KEYMAPS.put('j', Direction.DOWN);
    VALID_KEYMAPS.put('h', Direction.LEFT);
    VALID_KEYMAPS.put('l', Direction.RIGHT);
  }

  private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

  public InputManager() {}

  public Direction getDirectionFromInput() {
    char c = ' ';
    while (!isValidInput(c)) {
      try {
        String line;
        do {
          line = reader.readLine();
        } while (line.length() == 0);
        c = line.charAt(line.length() - 1);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    return convertToDirection(c);
  }

  private boolean isValidInput(char c) {
    return VALID_KEYMAPS.containsKey(c);
  }

  private Direction convertToDirection(char c) {
    return VALID_KEYMAPS.get(c);
  }
}
