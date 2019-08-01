package tower.engine;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.entities.Creature;
import de.gurkenlabs.litiengine.entities.Spawnpoint;
import de.gurkenlabs.litiengine.resources.Resources;

import java.awt.*;
import java.util.logging.Logger;

public class Utils {
  private static final Logger log = Logger.getLogger(Utils.class.getName());

  public static void spawn(String name, Creature entity) {
    if (entity.isDead()) return;
    Spawnpoint point = Game.world().environment().getSpawnpoint(name);
    point.spawn(entity);
    entity.setX(entity.getX() - (entity.getWidth() - point.getWidth()) / 2);
    entity.setY(point.getY() - entity.getHeight());
  }

  public static void checkCorner(Creature entity) {
    Game.world().environment().getCollisionBoxes().stream()
        .filter(c -> c.getTags().contains("wall"))
        .filter(c -> entity.getHitBox().intersects(c.getCollisionBox()))
//        .peek(c -> log.info(entity + " is arrived to corner."))
        .forEach(c -> Game.world().environment().remove(entity));
  }

  public static Font fontNormal() {
    return Resources.fonts().get("PICO-8.ttf", 8.0f);
  }

  public static Font fontLarge() {
    return Resources.fonts().get("PICO-8.ttf", 16.0f);
  }

  public static double screenHeight() {
    return Game.screens().current().getHeight();
  }

  public static double screenWidth() {
    return Game.screens().current().getWidth();
  }
}
