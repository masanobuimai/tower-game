package tower.engine.entity;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.annotation.CollisionInfo;
import de.gurkenlabs.litiengine.annotation.CombatInfo;
import de.gurkenlabs.litiengine.annotation.EntityInfo;
import de.gurkenlabs.litiengine.entities.Creature;
import de.gurkenlabs.litiengine.graphics.OverlayPixelsImageEffect;
import de.gurkenlabs.litiengine.graphics.animation.IAnimationController;
import de.gurkenlabs.litiengine.graphics.emitters.Emitter;
import de.gurkenlabs.litiengine.graphics.emitters.FireEmitter;
import tower.Soldier;
import tower.engine.Utils;
import tower.engine.entity.ext.Charge;
import tower.engine.entity.ext.FloatingTextEmitter;

import java.awt.*;
import java.util.logging.Logger;

@EntityInfo(width = 18, height = 18)
@CollisionInfo(collisionBoxWidth = 16, collisionBoxHeight = 18, collision = false)
@CombatInfo(hitpoints = 100)
public abstract class MobEntity extends Creature implements IUpdateable {
  private static final Logger log = Logger.getLogger(MobEntity.class.getName());

  public static final int LEFT_SIDE = 0;
  public static final int RIGHT_SIDE = 1;

  private Charge charge;
  protected int damage = Soldier.DEFAULT_POWER;

  public MobEntity(String name, int team) {
    this(name, team, Soldier.DEFAULT_SPEED);
  }

  public MobEntity(String name, int team, int speed) {
    super(name);
    setVelocity(speed);
    charge = new Charge(this);

    setTeam(team);
    if (!Game.isInNoGUIMode()) {
      addHitListener(e -> {
        Game.world().environment().add(new FloatingTextEmitter(String.valueOf((int) e.getDamage()),
                                                               e.getEntity().getCenter(), Color.WHITE));
        IAnimationController controller = e.getEntity().getAnimationController();
        controller.add(new OverlayPixelsImageEffect(50, Color.WHITE));
        Game.loop().perform(50, () -> controller.add(new OverlayPixelsImageEffect(50, Color.RED)));
        if (e.getAbility() != null && e.getAbility().getExecutor() instanceof StrikerEntity) {
          Emitter emitter = new FireEmitter((int) getX(), (int) getY());
          emitter.setHeight(getHeight());
          emitter.setTimeToLive(1500);
          Game.world().environment().add(emitter);
        }
      });
      addDeathListener(e -> {
        Game.world().environment().remove(e);
      });
    }
  }

  public boolean isEngage(Creature enemy) {
    return charge.isEngage(enemy);
  }

  public int damage() {
    return damage;
  }

  @Override
  public void update() {
    if (isDead()) {
      return;
    }
    charge.cast();
    Game.physics().move(this, this.getTickVelocity());
    Utils.checkCorner(this);
  }
}
