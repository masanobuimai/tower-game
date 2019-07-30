package tutorial.step4;

import tower.EarthShake;
import tower.Recovery;
import tower.RushAttack;
import tower.Soldier;
import tower.Tower;
import tower.TowerException;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

// Recoveryインターフェイスを実装すると，ライフ回復が可能になる
// EarthShakeインターフェイスを実装すると，地面が揺れる
// RushAttackインターフェイスを実装すると，突進攻撃ができる
public class MyTower extends Tower implements Recovery, EarthShake, RushAttack {
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
  public Map<Type, Boolean> targets() {
    HashMap<Type, Boolean> result = new HashMap<>();
    result.put(Type.TOWER, Boolean.TRUE);
    result.put(Type.SOLDERS, Boolean.TRUE);
    result.put(Type.ENEMIES, Boolean.TRUE);
    return result;
  }

  @Override
  public void shake(Ground ground) {
    try {
      ground.shake();
    } catch (TowerException ignore) {
    }
  }

  @Override
  public boolean attack(Enemy enemy) {
    return enemy.getHpRatio() > 0.2;
  }
}
