package tutorial.step3;

import tower.Soldier;
import tower.Tower;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MyTower2 extends Tower {
  private List<Soldier> list;

  public MyTower2() {
    list = new ArrayList<>();
    for (int i = 0; i < MAX_SOLDIER_COUNT; i++) {
      Soldier soldier = new Soldier();
      if (i % 3 == 1) {
        soldier.powerUp();
      } else if (i % 3 == 2) {
        soldier.speedUp();
      }
      list.add(soldier);
    }
    Collections.shuffle(list);
  }

  @Override
  public List<Soldier> getSoldierList() {
    return list;
  }
}
