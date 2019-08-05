package tower;

import de.gurkenlabs.litiengine.Game;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tower.engine.GM;
import tower.engine.entity.SoldierEntity;

import static org.assertj.core.api.Assertions.*;

public class SoldierTest {
  @BeforeAll
  static void beforeAll() {
    if (Game.hasStarted()) return;
    GM.start(new BasicTower(), "-nogui");
  }

  @DisplayName("パラメタ調整")
  @Test
  void parameter() {
    Soldier soldier = new Soldier();
    assertThat(soldier.getLife()).isEqualTo(100);
    assertThat(soldier.getSpeed()).isEqualTo(60);
    assertThat(soldier.getPower()).isEqualTo(20);

    soldier.speedUp();  // スピードアップ
    assertThat(soldier.getLife()).isEqualTo(100);
    assertThat(soldier.getSpeed()).isEqualTo(120);
    assertThat(soldier.getPower()).isEqualTo(10);

    soldier.speedUp();  // ２回やっても変わりない
    assertThat(soldier.getLife()).isEqualTo(100);
    assertThat(soldier.getSpeed()).isEqualTo(120);
    assertThat(soldier.getPower()).isEqualTo(10);

    soldier.powerUp();  // パワーアップ
    assertThat(soldier.getLife()).isEqualTo(100);
    assertThat(soldier.getSpeed()).isEqualTo(30);
    assertThat(soldier.getPower()).isEqualTo(40);

    soldier.powerUp();  // ２回やっても変わりない
    assertThat(soldier.getLife()).isEqualTo(100);
    assertThat(soldier.getSpeed()).isEqualTo(30);
    assertThat(soldier.getPower()).isEqualTo(40);
  }

  @DisplayName("ボーナス調整")
  @Test
  void bonus() {
    Soldier soldier75 = new Soldier(Soldier.LIFE_BONUS.Rate75);
    assertThat(soldier75.getLife()).isEqualTo(75);
    assertThat(soldier75.getSpeed()).isEqualTo(90);
    assertThat(soldier75.getPower()).isEqualTo(30);
    soldier75.speedUp();  // スピードアップ
    assertThat(soldier75.getLife()).isEqualTo(75);
    assertThat(soldier75.getSpeed()).isEqualTo(180);
    assertThat(soldier75.getPower()).isEqualTo(15);
    soldier75.powerUp();  // パワーアップ
    assertThat(soldier75.getLife()).isEqualTo(75);
    assertThat(soldier75.getSpeed()).isEqualTo(45);
    assertThat(soldier75.getPower()).isEqualTo(60);

    Soldier soldier150 = new Soldier(Soldier.LIFE_BONUS.Rate150);
    assertThat(soldier150.getLife()).isEqualTo(150);
    assertThat(soldier150.getSpeed()).isEqualTo(45);
    assertThat(soldier150.getPower()).isEqualTo(15);
    soldier150.speedUp();  // スピードアップ
    assertThat(soldier150.getLife()).isEqualTo(150);
    assertThat(soldier150.getSpeed()).isEqualTo(90);
    assertThat(soldier150.getPower()).isEqualTo(7);
    soldier150.powerUp();  // パワーアップ
    assertThat(soldier150.getLife()).isEqualTo(150);
    assertThat(soldier150.getSpeed()).isEqualTo(22);
    assertThat(soldier150.getPower()).isEqualTo(30);
  }

  @DisplayName("SoldierEntityと連係してない")
  @Test
  void unlink() {
    Soldier soldier = new Soldier();
    assertThat(soldier.getLife()).isEqualTo(100);
    assertThat(soldier.toString()).contains("待機中");
    assertThat(soldier.isDead()).isFalse();
  }

  @DisplayName("SoldierEntityと連動している")
  @Test
  void link() {
    Soldier soldier = new Soldier();
    SoldierEntity soldierEntity = new SoldierEntity(soldier);
    assertThat(soldier.toString()).contains("出兵済み");
    assertThat(soldierEntity.getHitPoints().getCurrentValue()).isEqualTo(soldier.getLife());
    soldierEntity.hit(50);
    assertThat(soldierEntity.getHitPoints().getCurrentValue()).isEqualTo(soldier.getLife());
    soldierEntity.hit(-10);
    assertThat(soldierEntity.getHitPoints().getCurrentValue()).isEqualTo(60);
    assertThat(soldier.isDead()).isFalse();
    soldierEntity.hit(100);
    assertThat(soldierEntity.getHitPoints().getCurrentValue()).isEqualTo(soldier.getLife());
    assertThat(soldier.isDead()).isTrue();
  }
}
