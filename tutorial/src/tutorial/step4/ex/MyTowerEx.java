package tutorial.step4.ex;

import tower.Soldier;
import tower.Tower;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MyTowerEx extends Tower implements CheckedEarthShake {
  private List<Soldier> list;

  public MyTowerEx() {
    list = IntStream.range(0, MAX_SOLDIER_COUNT)
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
  }

  @Override
  public List<Soldier> getSoldierList() {
    return list;
  }


  @Override
  public void checkedShake(CheckedGround ground) {
    try {
      ground.shake();
    } catch (CheckedGroundException e) {
      e.printStackTrace();
    }
  }
}
