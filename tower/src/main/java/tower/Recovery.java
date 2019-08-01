package tower;

import java.util.Map;

/** 回復アビリティ */
public interface Recovery {
  /** 使用回数 */
  int MAX_RECOVERY_COUNT = 2;

  /**
   * 回復対象を指定します。
   * Mapの値に true を設定した対象が回復対象になります。
   * <ul>
   *   <li>@{link {@link Type#TOWER}} - タワーの耐久力を回復します</li>
   *   <li>@{link {@link Type#SOLDERS} - ステージにいる全兵士の体力を回復します</li>
   *   <li>@{link {@link Type#ENEMIES} - ステージにいるすべての敵の体力を削ります</li>
   * </ul>
   * 回復対象の指定が多いほど，その効果が弱まります。
   * @return 回復対象
   */
  Map<Type, Boolean> targets();

  /** 回復対象 */
  enum Type {
    /** タワー */
    TOWER,
    /** 兵士 */
    SOLDERS,
    /** 敵 */
    ENEMIES;
  }
}
