package tower;

import tower.engine.GM;

import java.util.function.Supplier;

public class TowerGame {
  public static void start() {
    BasicTower tower = new BasicTower();
    tower.setName("");
    tower.setMaxLife(1);
    GM.start(tower);
  }

  public static void start(Tower tower) {
    GM.start(tower);
  }

  public static void start(Supplier<Tower> tower) {
    GM.start(tower);
  }

}
