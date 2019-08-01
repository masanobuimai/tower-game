package tower;

import tower.engine.entity.SoldierEntity;

public class Soldier {
  public static final int DEFAULT_LIFE = 100;
  public static final int DEFAULT_SPEED = 60;
  public static final int DEFAULT_POWER = 20;
  private int life = DEFAULT_LIFE;
  private int speed = DEFAULT_SPEED;
  private int power = DEFAULT_POWER;

  private SoldierEntity entity;

  public final int life() { return life;}

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

  public final void hit(int damage) {
    life -= damage;
    life = life < 0 ? 0 : life;
  }

}
