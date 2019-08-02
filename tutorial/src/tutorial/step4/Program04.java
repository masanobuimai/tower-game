package tutorial.step4;

import tower.Soldier;
import tower.Tower;
import tower.TowerGame;

import java.util.logging.Logger;

public class Program04 {
  private static final Logger log = Logger.getLogger(Program04.class.getName());

  public static void main(String[] args) {
    Tower tower = new MyTower();
//    Tower tower = new MyTowerEx();
    TowerGame.start(tower);
    for (Soldier soldier : tower.getSoldierList()) {
      log.info("soldier = " + soldier);
    }
  }
}
