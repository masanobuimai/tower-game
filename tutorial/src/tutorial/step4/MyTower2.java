package tutorial.step4;

import tower.Soldier;
import tower.Tower;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MyTower2 extends Tower {
  @Override
  public List<Soldier> getSoldierList() {
    List<Soldier> list = IntStream.rangeClosed(0, MAX_SOLDIER_COUNT)
                                  .mapToObj(i -> i % 3 == 0 ? new Soldier()
                                                            : i % 3 == 1 ? new PowerSoldier()
                                                                         : new SpeedSoldier())
                                  .collect(Collectors.toList());
    Collections.shuffle(list);
    return list;
  }
}
