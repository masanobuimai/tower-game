package tower.engine.entity;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.annotation.CollisionInfo;
import de.gurkenlabs.litiengine.annotation.CombatInfo;
import de.gurkenlabs.litiengine.annotation.EntityInfo;
import de.gurkenlabs.litiengine.entities.Creature;
import de.gurkenlabs.litiengine.graphics.OverlayPixelsImageEffect;
import de.gurkenlabs.litiengine.graphics.animation.IAnimationController;
import de.gurkenlabs.litiengine.graphics.emitters.Emitter;
import de.gurkenlabs.litiengine.graphics.emitters.FireEmitter;
import de.gurkenlabs.litiengine.graphics.emitters.ShimmerEmitter;
import tower.Tower;
import tower.engine.Utils;

import java.awt.*;

@EntityInfo(width = 52, height = 41)
@CollisionInfo(collisionBoxWidth = 16, collisionBoxHeight = 18, collision = false)
@CombatInfo(hitpoints = 1000)
public class TowerEntity extends Creature {
  private static final int MAX_SOLDIER_COUNT = 30;
  private int countMax;
  private int count;
  private Tower tower;

  public TowerEntity(Tower tower) {
    super(tower != null ? tower.getName() : "");
    setTeam(MobEntity.LEFT_SIDE);
    setVelocity(0);
    this.count = 0;
    this.tower = tower;
    if (tower == null) {
      hit(999);
      this.countMax = 0;
    } else {
      this.countMax = Math.min(MAX_SOLDIER_COUNT, tower.getSoldierList().size());
    }
    addHitListener(e -> {
      IAnimationController controller = e.getEntity().getAnimationController();
      controller.add(new OverlayPixelsImageEffect(50, Color.WHITE));
      Game.loop().perform(50, () -> controller.add(new OverlayPixelsImageEffect(50, Color.RED)));
    });
    addDeathListener(e -> {
      Emitter emitter = new FireEmitter((int) e.getX(), (int) e.getY());
      emitter.setHeight(e.getHeight());
      Game.world().environment().add(emitter);
    });
  }

  public SoldierEntity getSoldierEntity() {
    if (count < countMax) {
      return new SoldierEntity(tower.getSoldierList().get(count++));
    } else {
      return null;
    }
  }

  public String soldierCount() {
    return String.valueOf(count + "/" + countMax);
  }

  public void consumePill() {
    ShimmerEmitter emitter = new ShimmerEmitter(this.getX(), this.getY());
    emitter.setTimeToLive(2000);
    Game.world().environment().add(emitter);
  }

  public void consumeShake() {
    Game.world().camera().shake(1.5, 30, 1000);
    Game.loop().perform(1000, () -> {
      Game.world().camera().setFocus(Game.world().environment().getCenter());
    });
  }

  public void consumeShoot() {
    Utils.spawn("tower", new SpecialEntity());
  }
}
