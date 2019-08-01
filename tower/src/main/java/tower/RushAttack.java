package tower;

/** 突進攻撃アビリティ */
public interface RushAttack {
  /** 使用回数 */
  int MAX_RUSH_ATTACK_COUNT = 5;

  /**
   * 敵を攻撃するか決めます。
   * 突進攻撃は攻撃するたび攻撃力が減っていきます。
   * @param enemy 攻撃対象の敵
   * @return true：攻撃する，false：見逃す
   */
  boolean attack(Enemy enemy);

  /** 敵 */
  interface Enemy {
    /** 体力 */
    int getHp();

    /** 体力（％） */
    double getHpRatio();

    /** タワーとの距離（％） */
    double getDestRatio();
  }
}
