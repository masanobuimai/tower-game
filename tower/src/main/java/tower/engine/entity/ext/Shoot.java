package tower.engine.entity.ext;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.abilities.Ability;
import de.gurkenlabs.litiengine.abilities.effects.Effect;
import de.gurkenlabs.litiengine.abilities.effects.EffectTarget;
import de.gurkenlabs.litiengine.annotation.AbilityInfo;
import tower.engine.entity.StrikerEntity;

import java.util.logging.Logger;

@AbilityInfo(name = "Shoot", cooldown = 60, duration = 60)
public class Shoot extends Ability {
  private static final Logger log = Logger.getLogger(Shoot.class.getName());

  private StrikerEntity striker;
  private static int damage = 100;

  public Shoot(StrikerEntity executor) {
    super(executor);
    striker = executor;
    this.addEffect(new ShootEffect(this));
  }

  public class ShootEffect extends Effect {
    public ShootEffect(final Ability ability) {
      super(ability, EffectTarget.EXECUTINGENTITY);
    }

    @Override
    public void update() {
      super.update();
      Game.world().environment()
          .findCombatEntities(striker.getCollisionBox(),
                              combatEntity -> striker.getTeam() != combatEntity.getTeam()).stream()
          .filter(e -> striker.checkTarget(e))
          .forEach(e -> e.hit(striker.getDamage(), getAbility()));
    }
  }
}

