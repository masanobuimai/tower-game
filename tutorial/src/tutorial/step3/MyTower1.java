package tutorial.step3;

import tower.Soldier;
import tower.Tower;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MyTower1 extends Tower {
  @Override
  public List<Soldier> getSoldierList() {
    List<Soldier> list = new ArrayList<>();
    for (int i = 0; i < MAX_SOLDIER_COUNT; i++) {
      Soldier soldier = new Soldier();
      if (i % 3 == 1) {
        soldier.powerType();
      } else if (i % 3 == 2) {
        soldier.speedType();
      }
      list.add(soldier);
    }
    Collections.shuffle(list);
    return list;
  }
}
