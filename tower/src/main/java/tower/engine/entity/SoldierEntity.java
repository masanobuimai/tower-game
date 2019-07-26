package tower.engine.entity;

import tower.Soldier;

import java.util.logging.Logger;

public class SoldierEntity extends MobEntity {
  private static final Logger log = Logger.getLogger(SoldierEntity.class.getName());

  private Soldier soldier;

  public SoldierEntity(Soldier soldier) {
    super("Dean", LEFT_SIDE, soldier.speed());
    this.soldier = soldier;
    damage = soldier.power();
  }
}
