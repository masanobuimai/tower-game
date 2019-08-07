package tower;

import tower.engine.entity.SoldierEntity;

import java.util.function.Supplier;

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

  /** 体力ボーナス */
  public enum LIFE_BONUS {
    /** 体力25%減，それ以外50%アップ */
    Rate75(0.75, 1.5),
    /** ボーナスなし */
    Rate100(1.0, 1.0),
    /** 体力50アップ，それ以外25%減 */
    Rate150(1.5, 0.75);

    private double lifeRate, paramRate;

    LIFE_BONUS(double l, double p) {
      this.lifeRate = l;
      this.paramRate = p;
    }

    int life(int v) { return (int) (v * lifeRate); }

    int param(int v) { return (int) (v * paramRate); }
  }

  private LIFE_BONUS bonus;
  private String name;
  private int life, speed, power;

  private Supplier<Integer> defaultLife = () -> bonus.life(DEFAULT_LIFE);
  private Supplier<Integer> defaultSpeed = () -> bonus.param(DEFAULT_SPEED);
  private Supplier<Integer> defaultPower = () -> bonus.param(DEFAULT_POWER);

  /** 見た目 */
  public enum LOOKS {
    /** タイプA */ TYPE_A("soldierA"),
    /** タイプB */ TYPE_B("soldierB"),
    /** タイプC */ TYPE_C("soldierC");
    private String sprite;

    LOOKS(String name) { this.sprite = name; }

    @Override
    public String toString() {
      return sprite;
    }
  }

  private LOOKS looks;

  private SoldierEntity entity;

  /** 体力100，歩く速さ60，攻撃力20の兵士を作る。 */
  public Soldier() {
    this(LIFE_BONUS.Rate100);
  }

  /**
   * 体力ボーナスを付与した兵士を作る。
   * <table>
   *   <caption>_</caption>
   *   <tr>
   *     <th>体力ボーナス</th><th>体力</th><th>歩く速さ</th><th>攻撃力</th>
   *   </tr>
   *   <tr>
   *     <td>@{link {@link LIFE_BONUS#Rate75}}</td><td>75</td><td>90</td><td>30</td>
   *     <td>@{link {@link LIFE_BONUS#Rate100}}</td><td>100</td><td>60</td><td>20</td>
   *     <td>@{link {@link LIFE_BONUS#Rate150}}</td><td>150</td><td>45</td><td>15</td>
   *   </tr>
   *
   * </table>
   * @param bonus 体力ボーナス
   */
  public Soldier(LIFE_BONUS bonus) {
    this.name = "兵士#" + (count++);
    this.bonus = bonus;
    this.life = defaultLife.get();
    this.speed = defaultSpeed.get();
    this.power = defaultPower.get();
    this.looks = LOOKS.TYPE_A;
  }

  /**
   * 体力を取得します。
   * @return 体力
   */
  public final int getLife() {
    return entity != null ? entity.getHitPoints().getCurrentValue() : life;
  }

  /**
   * 歩く速さを取得します。
   * @return 歩く速さ
   */
  public final int getSpeed() { return speed;}

  /**
   * 攻撃力を取得します。
   * @return 攻撃力
   */
  public final int getPower() { return power;}

  /**
   * 見た目を取得します。
   * @return 見た目
   */
  public LOOKS getLooks() {
    return looks;
  }

  /** パラメタ（歩く速さと攻撃力）をリセットします。 */
  public final void reset() {
    speed = defaultSpeed.get();
    power = defaultPower.get();
  }

  /** パワーアップする（速さ半減，攻撃力２倍）。 */
  public final void powerUp() {
    speed = defaultSpeed.get() / 2;
    power = defaultPower.get() * 2;
  }

  /** スピードアップする（速さ２倍，攻撃力半減）。 */
  public final void speedUp() {
    speed = defaultSpeed.get() * 2;
    power = defaultPower.get() / 2;
  }

  /**
   * 見た目を設定します。
   * @param looks 見た目
   */
  public void setLooks(LOOKS looks) {
    this.looks = looks;
  }

  /**
   * 死んじゃったかどうかを確認します。
   * @return 体力が0だと死亡
   */
  public final boolean isDead() {
    return getLife() <= 0;
  }

  @Override
  public String toString() {
    return String.format("%s(速さ:%d/攻撃力:%d) - ", name, speed, power)
           + (entity == null ? "待機中" : "出兵済み" + (isDead() ? "[死亡]" : "[生還！]"));
  }
}
