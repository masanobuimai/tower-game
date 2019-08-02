package tower.engine.entity;

import tower.Soldier;

import java.lang.reflect.Field;
import java.util.logging.Logger;

public class SoldierEntity extends MobEntity {
  private static final Logger log = Logger.getLogger(SoldierEntity.class.getName());

  private Soldier soldier;

  public SoldierEntity(Soldier soldier) {
    super("Dean", LEFT_SIDE, soldier.speed());
    this.soldier = soldier;
    getHitPoints().setBaseValue(soldier.life());
    damage = soldier.power();
    try {
      Field field = Soldier.class.getDeclaredField("entity");
      field.setAccessible(true);
      field.set(soldier, this);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
