package tower;

import de.gurkenlabs.litiengine.entities.ICombatEntity;

import java.util.Random;

/** 全体攻撃アビリティ */
public interface EarthShake {
  /** 使用回数 */
  int MAX_EARTH_SHAKE_COUNT = 3;

  /**
   * 地面を揺らしてステージにいるすべての敵にダメージを与えます。
   * @param ground 地面：この {@link Ground#shake()} を実行しないとダメージを与えることはできません。
   */
  void shake(Ground ground);

  /** 地面 */
  final class Ground {
    private ICombatEntity e;

    public Ground(ICombatEntity e) {
      this.e = e;
    }

    /**
     * 地面を揺らす
     * @throws TowerException たまに失敗します
     */
    public void shake() throws TowerException {
      if (Math.random() < 0.15) {
        throw new TowerException("ゴメン，失敗した。");
      }
      // 30～100の範囲でダメージを決定する
      e.hit((new Random().nextInt(7) + 1) * 10 + 30);
    }
  }
}
