package tutorial.step5;

import tower.EarthShake;
import tower.Recovery;
import tower.Soldier;
import tower.Tower;
import tower.TowerException;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

// Recoveryインターフェイスを実装すると，ライフ回復が可能になる
// EarthShakeインターフェイスを実装すると，地面が揺れる
public class MyTower extends Tower implements Recovery, EarthShake {
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

  @Override
  public void shake(Ground ground) {
    try {
      ground.shake();
    } catch (TowerException ignore) {
    }
  }
}
