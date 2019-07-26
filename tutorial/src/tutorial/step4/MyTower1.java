package tutorial.step4;

import tower.Soldier;
import tower.Tower;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MyTower1 extends Tower {
  @Override
  public List<Soldier> getSoldierList() {
    List<Soldier> list = IntStream.rangeClosed(0, MAX_SOLDIER_COUNT)
                                  .mapToObj(i -> {
                                    Soldier soldier = new Soldier();
                                    if (i % 3 == 1) {
                                      soldier.powerType();
                                    } else if (i % 3 == 2) {
                                      soldier.speedType();
                                    }
                                    return soldier;
                                  })
                                  .collect(Collectors.toList());
    Collections.shuffle(list);
    return list;
  }
}
