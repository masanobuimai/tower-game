package tower;

import de.gurkenlabs.litiengine.Game;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tower.engine.GM;

import static org.assertj.core.api.Assertions.*;

public class TowerTest {
  @BeforeAll
  static void beforeAll() {
    if (Game.hasStarted()) return;
    GM.start(new BasicTower(), "-nogui");
  }


  @DisplayName("BasicTowerのテスト")
  @Test
  void basicTower() {
    BasicTower tower = new BasicTower();
    assertThat(tower.getMaxLife()).isEqualTo(1_000);

    tower.setMaxLife(100);
    assertThat(tower.getMaxLife()).isEqualTo(100);

    tower.setMaxLife(0);
    assertThat(tower.getMaxLife()).isEqualTo(1);

    tower.setMaxLife(2_001);
    assertThat(tower.getMaxLife()).isEqualTo(2_000);

    assertThat(tower.getSoldierList()).isEmpty();

    Soldier soldier = new Soldier();
    tower.setSoldier(soldier);
    assertThat(tower.getSoldierList()).containsOnlyOnce(soldier);
  }

}
