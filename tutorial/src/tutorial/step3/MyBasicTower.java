package tutorial.step3;

import tower.BasicTower;
import tower.Soldier;

public class MyBasicTower extends BasicTower {
  @Override
  protected Soldier getSoldier() {
    Soldier soldier = new Soldier();
//    soldier.powerType();
    soldier.speedType();
    return soldier;
  }
}