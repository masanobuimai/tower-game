package tower;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class BasicTower extends Tower {
  private String name = "bunker";
  private static int MAX_LIFE_UPPER = 2_000;
  private int maxLife = 1_000;
  private Soldier soldier;

  public void setName(String name) {
    this.name = name;
  }

  // 1～MAX_LIFE_UPPERの範囲で設定する
  public void setMaxLife(int life) {
    maxLife = life < 1 ? 1
                       : MAX_LIFE_UPPER < life ? MAX_LIFE_UPPER : life;
  }

  public int getMaxLife() {
    return maxLife;
  }

  @Override
  public final List<Soldier> getSoldierList() {
    if (soldier != null) {
      return Stream.of(soldier).collect(Collectors.toList());
    } else {
      return Collections.emptyList();
    }
  }

  public void setSoldier(Soldier soldier) {
    this.soldier = soldier;
  }
}
