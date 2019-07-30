package tower;

import java.util.Map;

public interface Recovery {
  int MAX_RECOVERY_COUNT = 2;

  Map<Type, Boolean> targets();

  enum Type {
    TOWER, SOLDERS, ENEMIES;
  }
}
