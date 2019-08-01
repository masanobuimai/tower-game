package tower;

import java.util.List;

/**
 * タワーは拠点防衛の要です。
 * ここから兵士を派兵します。
 */
public abstract class Tower {
  /** 派兵できる兵士の最大数 */
  public static final int MAX_SOLDIER_COUNT = 40;

  /**
   * 派兵できる兵士の最大数は {@link #MAX_SOLDIER_COUNT} 人までです。
   * それ以上の兵数を返しても出兵しません。
   * @return 派兵する兵士の一覧
   */
  public abstract List<Soldier> getSoldierList();
}
