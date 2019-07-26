package tower;

import tower.engine.GM;

public class TowerGame {
  public static void start() {
    GM.start(null);
  }

  public static void start(Tower tower) {
    GM.start(tower);
  }
}
