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
import tower.BasicTower;
import tower.EarthShake;
import tower.Recovery;
import tower.RushAttack;
import tower.Tower;
import tower.TowerException;
import tower.engine.Utils;

import java.awt.*;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

@EntityInfo(width = 52, height = 41)
@CollisionInfo(collisionBoxWidth = 16, collisionBoxHeight = 18, collision = false)
@CombatInfo(hitpoints = 1000)
public class TowerEntity extends Creature {
  private static final Logger log = Logger.getLogger(TowerEntity.class.getName());

  private Tower tower;
  private int soldierCount;
  private int soldierCountMax;
  private int recoveryCount;
  private int shakeCount;
  private int rushCount;

  public TowerEntity(Tower tower) {
    super(tower.getName());
    setTeam(MobEntity.LEFT_SIDE);
    setVelocity(0);
    this.tower = tower;
    this.soldierCount = 0;
    this.soldierCountMax = Math.min(Tower.MAX_SOLDIER_COUNT, tower.getSoldierList().size());
    this.recoveryCount = canRecovery() ? 2 : 0;
    this.shakeCount = canShake() ? 3 : 0;
    this.rushCount = canRush() ? 5 : 0;

    if (tower instanceof BasicTower) {
      getHitPoints().setMaxValue(((BasicTower) tower).getMaxLife());
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
    if (soldierCount < soldierCountMax) {
      return new SoldierEntity(tower.getSoldierList().get(soldierCount++));
    } else {
      return null;
    }
  }

  public String soldierCount() {
    return String.valueOf(soldierCount + "/" + soldierCountMax);
  }

  public boolean canRecovery() {
    return tower instanceof Recovery;
  }

  public int getRecoveryCount() {
    return recoveryCount;
  }

  public void consumeRecovery() {
    if (canRecovery() && recoveryCount > 0) {
      recoveryCount--;
      getHitPoints().setToMaxValue();
      ShimmerEmitter emitter = new ShimmerEmitter(this.getX(), this.getY());
      emitter.setTimeToLive(2000);
      Game.world().environment().add(emitter);
    }
  }

  public boolean canShake() {
    return tower instanceof EarthShake;
  }

  public int getShakeCount() {
    return shakeCount;
  }

  public void consumeShake() {
    if (canShake() && shakeCount > 0) {
      shakeCount--;
      Game.world().camera().shake(1.5, 30, 1000);
      Game.loop().perform(1000, () -> {
        Game.world().camera().setFocus(Game.world().environment().getCenter());
      });
      Random random = new Random();
      try {
        Game.world().environment().getCombatEntities().stream()
            .filter(e -> e instanceof EnemyEntity)
            .forEach(e -> ((EarthShake) tower).shake(new EarthShake.Ground(e)));
      } catch (TowerException e) {
        die();
        log.log(Level.WARNING, "予期せぬエラーです。", e);
      }
    }
  }

  public boolean canRush() {
    return tower instanceof RushAttack;
  }

  public int getRushCount() {
    return rushCount;
  }

  public void consumeRush() {
    if (canRush() && rushCount > 0) {
      rushCount--;
      Utils.spawn("tower", new StrikerEntity((RushAttack) tower));
    }
  }
}
