package tower;

import tower.engine.GameManager;

public class TowerGame {
  public static void start() {
    GameManager.start(null);
  }

  public static void start(Tower tower) {
    GameManager.start(tower);
  }
}
