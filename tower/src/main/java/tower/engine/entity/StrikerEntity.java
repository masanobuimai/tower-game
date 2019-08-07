package tower.engine.entity;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.annotation.CollisionInfo;
import de.gurkenlabs.litiengine.annotation.EntityInfo;
import de.gurkenlabs.litiengine.annotation.MovementInfo;
import de.gurkenlabs.litiengine.entities.Creature;
import de.gurkenlabs.litiengine.entities.ICombatEntity;
import tower.RushAttack;
import tower.engine.GM;
import tower.engine.Utils;
import tower.engine.entity.ext.Shoot;

import java.util.logging.Level;
import java.util.logging.Logger;

@EntityInfo(width = 18, height = 18)
@MovementInfo(velocity = 260)
@CollisionInfo(collisionBoxWidth = 16, collisionBoxHeight = 18, collision = false)
public class StrikerEntity extends Creature implements IUpdateable {
  private static final Logger log = Logger.getLogger(StrikerEntity.class.getName());

  private Shoot shoot;
  private RushAttack rush;
  private int damage = 120;

  public StrikerEntity(RushAttack rush) {
    super("striker");
    setTeam(MobEntity.LEFT_SIDE);
    shoot = new Shoot(this);
    this.rush = rush;
    addDeathListener(e -> {
      Game.world().environment().remove(e);
    });
  }

  @Override
  public void update() {
    if (isDead()) {
      return;
    }
    shoot.cast();
    Game.physics().move(this, this.getTickVelocity());
    Utils.checkCorner(this);
  }

  public int getDamage() {
    int result = damage;
    damage = (int) (damage * 0.9);
    return result;
  }

  public boolean checkTarget(ICombatEntity entity) {
    try {
      return rush.attack(new Enemy(entity));
    } catch (Exception e) {
      GM.tower().die();
      log.log(Level.WARNING, "予期せぬエラーです。", e);
      return false;
    }
  }

  public static class Enemy implements RushAttack.Enemy {
    private ICombatEntity e;

    public Enemy(ICombatEntity e) {
      this.e = e;
    }

    @Override
    public int getHp() {
      return e.getHitPoints().getCurrentValue();
    }

    @Override
    public double getHpRatio() {
      return getHp() / e.getHitPoints().getMaxValue();
    }

    @Override
    public double getDestRatio() {
      double destMax = Game.screens().current().getWidth() - GM.tower().getCenter().getX();
      double dest = e.getCenter().getX() - GM.tower().getCenter().getX();
      return Math.abs(dest / destMax);
    }
  }
}
