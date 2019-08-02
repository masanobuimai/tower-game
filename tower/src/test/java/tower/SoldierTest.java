package tower;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tower.engine.GM;
import tower.engine.entity.SoldierEntity;

import static org.assertj.core.api.Assertions.*;

public class SoldierTest {
  @BeforeAll
  static void beforeAll() {
    GM.start(new BasicTower(), "-nogui");
  }

  @DisplayName("SoldierEntityと連係してない")
  @Test
  void unlink() {
    Soldier soldier = new Soldier();
    assertThat(soldier.life()).isEqualTo(100);
    assertThat(soldier.toString()).contains("待機中");
    assertThat(soldier.isDead()).isFalse();
  }

  @DisplayName("SoldierEntityと連動している")
  @Test
  void link() {
    Soldier soldier = new Soldier();
    SoldierEntity soldierEntity = new SoldierEntity(soldier);
    assertThat(soldier.toString()).contains("出兵済み");
    assertThat(soldierEntity.getHitPoints().getCurrentValue()).isEqualTo(soldier.life());
    soldierEntity.hit(50);
    assertThat(soldierEntity.getHitPoints().getCurrentValue()).isEqualTo(soldier.life());
    soldierEntity.hit(-10);
    assertThat(soldierEntity.getHitPoints().getCurrentValue()).isEqualTo(60);
    assertThat(soldier.isDead()).isFalse();
    soldierEntity.hit(100);
    assertThat(soldierEntity.getHitPoints().getCurrentValue()).isEqualTo(soldier.life());
    assertThat(soldier.isDead()).isTrue();
  }
}
