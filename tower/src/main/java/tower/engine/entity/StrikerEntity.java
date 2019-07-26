package tower.engine.entity;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.annotation.CollisionInfo;
import de.gurkenlabs.litiengine.annotation.EntityInfo;
import de.gurkenlabs.litiengine.annotation.MovementInfo;
import de.gurkenlabs.litiengine.entities.Creature;
import tower.engine.Utils;
import tower.engine.entity.ext.Shoot;

import java.util.logging.Logger;

@EntityInfo(width = 18, height = 18)
@MovementInfo(velocity = 260)
@CollisionInfo(collisionBoxWidth = 16, collisionBoxHeight = 18, collision = false)
public class StrikerEntity extends Creature implements IUpdateable {
  private static final Logger log = Logger.getLogger(StrikerEntity.class.getName());

  private Shoot shoot;

  public StrikerEntity() {
    super("gurknukem");
    setTeam(MobEntity.LEFT_SIDE);
    shoot = new Shoot(this);
    addDeathListener(e -> {
      log.info(() -> e + " is dead...");
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
}
