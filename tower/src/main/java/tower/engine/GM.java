package tower.engine;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.graphics.Camera;
import de.gurkenlabs.litiengine.input.Input;
import de.gurkenlabs.litiengine.resources.Resources;
import tower.Tower;
import tower.engine.entity.EnemyEntity;
import tower.engine.entity.TowerEntity;
import tower.engine.ui.MainScreen;
import tower.engine.ui.TitleScreen;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.logging.LogManager;
import java.util.logging.Logger;

// ゲームマスター
public class GM {
  private static final Logger log = Logger.getLogger(GM.class.getName());

  public enum GameState {
    READY, INGAME, GAMEOVER
  }

  private static GameState state;

  public static final int MAX_ENEMY_COUNT = 100;

  private static boolean timing() {
    return Game.loop().getTicks() % 30 == 0;
  }

  private static int enemyCount;

  private static Tower tower;
  private static TowerEntity towerEntity;

  public static TowerEntity tower() {
    return towerEntity;
  }

  public static void start(Tower _tower) {
    if (_tower == null) {
      throw new NullPointerException("null入れてくんな。");
    }
    state = GameState.READY;
    try (InputStream resource = GM.class.getResourceAsStream("/logging.properties")) {
      if (resource != null) {
        LogManager.getLogManager().readConfiguration(resource);
      }
    } catch (IOException ignore) {
    }
    URL resource = GM.class.getClassLoader().getResource("game.litidata");
    Resources.load(resource);

    Game.init();
    Game.graphics().setBaseRenderScale(1.0f);

    Game.screens().add(new MainScreen());
    Game.screens().add(new TitleScreen());

    Game.screens().display("title");
    Camera cam = new Camera();
    cam.setFocus(Game.world().environment().getCenter());
    Game.world().setCamera(cam);

    initInputDevice();

    tower = _tower;
    Game.start();
  }

  public static boolean gameover() {
    return state == GameState.GAMEOVER;
  }

  private static void initInputDevice() {
    Input.mouse().setGrabMouse(false);
    Input.keyboard().onKeyReleased(KeyEvent.VK_ESCAPE, e -> System.exit(0));
    Input.keyboard().onKeyReleased(KeyEvent.VK_SPACE, e -> startGame());
    Function<Runnable, Consumer<KeyEvent>> ke = r ->
        e -> { if (state == GameState.INGAME && !tower().isDead()) r.run(); };
    Input.keyboard().onKeyReleased(KeyEvent.VK_F1,
                                   ke.apply(() -> towerEntity.consumeRecovery()));
    Input.keyboard().onKeyReleased(KeyEvent.VK_F2,
                                   ke.apply(() -> towerEntity.consumeShake()));
    Input.keyboard().onKeyReleased(KeyEvent.VK_F3,
                                   ke.apply(() -> towerEntity.consumeRush()));
  }

  private static void startGame() {
    log.info(() -> "state:" + state);
    if (state == GameState.INGAME) return;

    // TODO ゲームオーバー後の再開がヘンなので何とかしたい
    state = GameState.INGAME;
    enemyCount = 0;
    towerEntity = new TowerEntity(tower);
    Game.window().getRenderComponent().fadeOut(500);
    Game.loop().perform(600, () -> {
      Game.screens().display("main");
      Game.window().getRenderComponent().fadeIn(500);
      Utils.spawn("tower", towerEntity);
    });
  }

  public static void update() {
    if (timing()) {
      if (!towerEntity.isDead()) {
        Optional.ofNullable(towerEntity.getSoldierEntity())
                .ifPresent(e -> Utils.spawn("respawn", e));
      }
      if (enemyCount < MAX_ENEMY_COUNT) {
        Utils.spawn("spawn", new EnemyEntity());
        enemyCount++;
      }
    }
    if (towerEntity.isDead()
        || enemyCount >= MAX_ENEMY_COUNT && Game.world().environment().getCombatEntities().size() == 1) {
      state = GameState.GAMEOVER;
    }
  }

  public static String soldierCount() {
    return towerEntity.soldierCount();
  }

  public static String enemyCount() {
    return String.valueOf(enemyCount + "/" + MAX_ENEMY_COUNT);
  }

  public static Pair<Boolean, Integer> abilityRecover() {
    return new Pair<>(towerEntity != null && towerEntity.canRecovery(),
                      towerEntity != null ? towerEntity.getRecoveryCount() : 0);
  }

  public static Pair<Boolean, Integer> abilityShake() {
    return new Pair<>(towerEntity != null && towerEntity.canShake(),
                      towerEntity != null ? towerEntity.getShakeCount() : 0);
  }

  public static Pair<Boolean, Integer> abilityRush() {
    return new Pair<>(towerEntity != null && towerEntity.canRush(),
                      towerEntity != null ? towerEntity.getRushCount() : 0);
  }

  public static class Pair<T1, T2> {
    public final T1 v1;
    public final T2 v2;

    Pair(T1 v1, T2 v2) {
      this.v1 = v1;
      this.v2 = v2;
    }
  }
}
