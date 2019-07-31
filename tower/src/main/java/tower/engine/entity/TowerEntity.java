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
import tower.Soldier;
import tower.Tower;
import tower.engine.Utils;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
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
  private Collection<SoldierEntity> activeSoldiers;

  public TowerEntity(Tower tower) {
    super("bunker");
    setName("bunker");
    setTeam(MobEntity.LEFT_SIDE);
    setVelocity(0);
    this.tower = tower;
    this.soldierCount = 0;
    this.soldierCountMax = Math.min(Tower.MAX_SOLDIER_COUNT, tower.getSoldierList().size());
    this.recoveryCount = canRecovery() ? Recovery.MAX_RECOVERY_COUNT : 0;
    this.shakeCount = canShake() ? EarthShake.MAX_EARTH_SHAKE_COUNT : 0;
    this.rushCount = canRush() ? RushAttack.MAX_RUSH_ATTACK_COUNT : 0;
    this.activeSoldiers = new ArrayList<>();

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
      SoldierEntity newSoldier = new SoldierEntity(tower.getSoldierList().get(soldierCount++));
      activeSoldiers.add(newSoldier);
      return newSoldier;
    } else {
      return null;
    }
  }

  public String soldierCount() {
    return soldierCount + "/" + soldierCountMax;
  }

  public int getSoldierCount() {
    return activeSoldiers.size();
  }

  public int getDeadSoldierCount() {
    return (int) activeSoldiers.stream()
                               .filter(s -> s.isDead()).count();
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
      Map<Recovery.Type, Boolean> targets = ((Recovery) tower).targets();
      double ratio = targets.values().stream().filter(v -> v).count() / Recovery.Type.values().length;
      targets.entrySet().stream()
             .filter(m -> m.getKey() == Recovery.Type.TOWER && m.getValue())
             .forEach(m -> {
               int hp = (int) (getHitPoints().getMaxValue() * ratio);
               getHitPoints().setBaseValue(hp);
               ShimmerEmitter emitter = new ShimmerEmitter(this.getX(), this.getY());
               emitter.setTimeToLive(2000);
               Game.world().environment().add(emitter);
             });
      targets.entrySet().stream()
             .filter(m -> m.getKey() == Recovery.Type.SOLDERS && m.getValue())
             .forEach(m -> {
               int hp = (int) (new SoldierEntity(new Soldier()).getHitPoints().getMaxValue() * ratio);
               Game.world().environment().getCombatEntities().stream()
                   .filter(e -> e instanceof SoldierEntity)
                   .forEach(e -> {
                     e.getHitPoints().setBaseValue(hp);
                     ShimmerEmitter emitter = new ShimmerEmitter(e.getX(), e.getY());
                     emitter.setTimeToLive(1000);
                     Game.world().environment().add(emitter);
                   });
             });
      targets.entrySet().stream()
             .filter(m -> m.getKey() == Recovery.Type.ENEMIES && m.getValue())
             .forEach(m -> {
               Game.world().environment().getCombatEntities().stream()
                   .filter(e -> e instanceof EnemyEntity)
                   .forEach(e -> {
                     int damage = (int) (e.getHitPoints().getCurrentValue() / (2 * ratio));
                     e.hit(damage);
                   });
             });
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
      } catch (Exception e) {
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

  public int score() {
    int point = 0;
    point += getHitPoints().getCurrentValue() * 2;  // 残りライフの2倍
    if (!isDead()) point += 10_000;               // 勝利ボーナス 10,000点
    point += (getSoldierCount() - getDeadSoldierCount()) * 1000;  // 生存兵×1,000点

    // アビリティの残り回数×5,000点
    point += getRecoveryCount() * 5000;
    point += getShakeCount() * 5000;
    point += getRushCount() * 5000;

    return point;
  }
}
