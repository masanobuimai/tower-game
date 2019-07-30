package tower;

import tower.engine.entity.SoldierEntity;

public class Soldier {
  public static final int DEFAULT_SPEED = 60;
  public static final int DEFAULT_POWER = 20;
  private int speed = DEFAULT_SPEED;
  private int power = DEFAULT_POWER;

  private SoldierEntity entity;

  public final int speed() { return speed;}

  public final int power() { return power;}

  public final void standard() {
    speed = DEFAULT_SPEED;
    power = DEFAULT_POWER;
  }

  public final void powerType() {
    speed = DEFAULT_SPEED / 2;
    power = DEFAULT_POWER * 2;
  }

  public final void speedType() {
    speed = DEFAULT_SPEED * 2;
    power = DEFAULT_POWER / 2;
  }

  public final boolean dispatched() { return entity != null;}

  public final String getName() {
    return dispatched() ? entity.getName() : "未定義";
  }

  public final int getLife() {
    return dispatched() ? entity.getHitPoints().getCurrentValue() : -1;
  }

  public final int getMaxLife() {
    return dispatched() ? entity.getHitPoints().getMaxValue() : -1;
  }

  public final Boolean isDead() {
    return dispatched() ? entity.isDead() : null;
  }
}
