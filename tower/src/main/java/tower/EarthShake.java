package tower;

import de.gurkenlabs.litiengine.entities.ICombatEntity;

import java.util.Random;

public interface EarthShake {
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
      e.hit((new Random().nextInt(2) + 1) * 10);
    }
  }
}
