package tower;

import tower.engine.entity.SoldierEntity;

/**
 * 兵士。
 * 属性に体力（life），歩く速さ（speed），攻撃力（power）を持ちます。
 */
public class Soldier {
  /** 体力のデフォルト値 */
  public static final int DEFAULT_LIFE = 100;
  /** 歩く速さのデフォルト値 */
  public static final int DEFAULT_SPEED = 60;
  /** 攻撃力のデフォルト値 */
  public static final int DEFAULT_POWER = 20;

  private static int count = 1;

  private String name;
  private int life = DEFAULT_LIFE;
  private int speed = DEFAULT_SPEED;
  private int power = DEFAULT_POWER;

  private SoldierEntity entity;

  public Soldier() {
    name = "兵士#" + (count++);
  }

  /**
   * 体力を取得します。
   * @return 体力
   */
  public final int life() {
    return entity != null ? entity.getHitPoints().getCurrentValue() : life;
  }

  /**
   * 歩く速さを取得します。
   * @return 歩く速さ
   */
  public final int speed() { return speed;}

  /**
   * 攻撃力を取得します。
   * @return 攻撃力
   */
  public final int power() { return power;}

  /** 標準的な兵士にします。 */
  public final void standard() {
    speed = DEFAULT_SPEED;
    power = DEFAULT_POWER;
  }

  /**
   * パワータイプ（速さ半減，攻撃力２倍）の兵士にします。
   */
  public final void powerType() {
    speed = DEFAULT_SPEED / 2;
    power = DEFAULT_POWER * 2;
  }

  /**
   * スピードタイプ（速さ２倍，攻撃力半減）の兵士にします。
   */
  public final void speedType() {
    speed = DEFAULT_SPEED * 2;
    power = DEFAULT_POWER / 2;
  }

  /**
   * 死んじゃったかどうかを確認します。
   * @return 体力が0だと死亡
   */
  public final boolean isDead() {
    return life() <= 0;
  }

  @Override
  public String toString() {
    return String.format("%s(速さ:%d/攻撃力:%d) - ", name, speed, power)
           + (entity == null ? "待機中" : "出兵済み" + (isDead() ? "[死亡]" : "[生還！]"));
  }
}
