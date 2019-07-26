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
import tower.Recovery;
import tower.Tower;
import tower.engine.Utils;

import java.awt.*;

@EntityInfo(width = 52, height = 41)
@CollisionInfo(collisionBoxWidth = 16, collisionBoxHeight = 18, collision = false)
@CombatInfo(hitpoints = 1000)
public class TowerEntity extends Creature {
  private Tower tower;
  private int soldierCount;
  private int soldierCountMax;
  private int recoveryCount;

  public TowerEntity(Tower tower) {
    super(tower != null ? tower.getName() : "");
    setTeam(MobEntity.LEFT_SIDE);
    setVelocity(0);
    this.tower = tower;
    this.soldierCount = 0;
    if (tower == null) {
      hit(999);
      this.soldierCountMax = 0;
    } else {
      this.soldierCountMax = Math.min(Tower.MAX_SOLDIER_COUNT, tower.getSoldierList().size());
    }
    recoveryCount = isRecoverable() ? 2 : 0;

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
    if (soldierCount < soldierCountMax) {
      return new SoldierEntity(tower.getSoldierList().get(soldierCount++));
    } else {
      return null;
    }
  }

  public String soldierCount() {
    return String.valueOf(soldierCount + "/" + soldierCountMax);
  }

  public boolean isRecoverable() {
    return tower != null && tower instanceof Recovery;
  }

  public int getRecoveryCount() {
    return recoveryCount;
  }

  public void consumeRecovery() {
    if (isRecoverable() && recoveryCount > 0) {
      recoveryCount--;
      getHitPoints().setToMaxValue();
      ShimmerEmitter emitter = new ShimmerEmitter(this.getX(), this.getY());
      emitter.setTimeToLive(2000);
      Game.world().environment().add(emitter);
    }
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
