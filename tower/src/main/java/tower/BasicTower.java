package tower;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 簡単なタワー。
 * 通常のタワーより倍の耐久力を設定できますが，兵士は０～１名しか派兵できません。
 */
public final class BasicTower extends Tower {
  /** 簡単なタワーの最大耐久力 */
  public static int MAX_LIFE_UPPER = 2_000;

  private String name = "bunker";
  private int maxLife = 1_000;
  private Soldier soldier;

  /**
   * タワーの名前を設定します。
   * @param name の名前
   */
  public void setName(String name) {
    // TODO タワーの見た目を2～3種類用意しよう
    this.name = name;
  }

  /**
   * タワーの耐久力を設定します。
   * 設定できる耐久力の最大値は {@link #MAX_LIFE_UPPER} までです
   * （普通のタワーの耐久力はこの半分です）。
   * @param life 耐久力：0より小さい値の場合，最小値（1）で設定します。
   */
  public void setMaxLife(int life) {
    maxLife = life < 1 ? 1
                       : MAX_LIFE_UPPER < life ? MAX_LIFE_UPPER : life;
  }

  /**
   * タワーの耐久力を取得します。
   * @return 耐久力
   */
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

  /**
   * 出兵する兵士を設定します。
   * このメソッドを一度も呼ばなかったり，兵士にnullを設定すると
   * このタワーからは兵士は出兵しません。
   * @param soldier 兵士
   */
  public void setSoldier(Soldier soldier) {
    this.soldier = soldier;
  }
}
