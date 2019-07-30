package tower;

import de.gurkenlabs.litiengine.entities.ICombatEntity;

import java.util.Random;

public interface EarthShake {
  int MAX_EARTH_SHAKE_COUNT = 3;

  void shake(Ground ground);

  final class Ground {
    private ICombatEntity e;

    public Ground(ICombatEntity e) {
      this.e = e;
    }

    public void shake() throws TowerException {
      if (Math.random() < 0.15) {
        throw new TowerException("ゴメン，失敗した。");
      }
      // 30～100の範囲でダメージを決定する
      e.hit((new Random().nextInt(7) + 1) * 10 + 30);
    }
  }
}
