package tutorial.step3;

import tower.Soldier;
import tower.Tower;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MyTower3 extends Tower {

  private List<Soldier> list;

  public MyTower3() {
    list = new ArrayList<>();
    for (int i = 0; i < MAX_SOLDIER_COUNT; i++) {
      Soldier soldier = new Soldier();
      if (i % 3 == 0) {
        list.add(new Soldier());
      } else if (i % 3 == 1) {
        list.add(new PowerSoldier());
      } else {
        list.add(new SpeedSoldier());
      }
    }
    Collections.shuffle(list);
  }

  @Override
  public List<Soldier> getSoldierList() {
    return list;
  }
}
