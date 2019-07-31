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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.logging.LogManager;
import java.util.logging.Logger;

// ゲームマスター
public class GM {
  private static final Logger log = Logger.getLogger(GM.class.getName());

  public enum State {READY, INGAME, GAMEOVER;}

  private static State state;

  public static final int MAX_ENEMY_COUNT = 100;

  private static boolean terminating;

  private static long gameStartTick;
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
    state = State.READY;
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

    // ムリヤリ同期処理ふうにみせる
    while (!terminating) {
      try {
        TimeUnit.MILLISECONDS.sleep(500);
      } catch (InterruptedException ignore) {
      }
    }
    ExecutorService es = Executors.newFixedThreadPool(1);
    es.execute(() -> {
      try {
        TimeUnit.SECONDS.sleep(2);
      } catch (InterruptedException ignore) {
      }
      System.exit(0);
    });
  }

  public static State getState() {
    return state;
  }

  private static void initInputDevice() {
    Input.mouse().setGrabMouse(false);
    Input.keyboard().onKeyReleased(KeyEvent.VK_SPACE, e -> startGame());
    Function<Runnable, Consumer<KeyEvent>> ke = r ->
        e -> { if (state == State.INGAME && !tower().isDead()) r.run(); };
    Input.keyboard().onKeyReleased(KeyEvent.VK_F1,
                                   ke.apply(() -> towerEntity.consumeRecovery()));
    Input.keyboard().onKeyReleased(KeyEvent.VK_F2,
                                   ke.apply(() -> towerEntity.consumeShake()));
    Input.keyboard().onKeyReleased(KeyEvent.VK_F3,
                                   ke.apply(() -> towerEntity.consumeRush()));
  }

  private static void startGame() {
    if (state == State.INGAME) return;
    Game.window().getRenderComponent().fadeOut(500);
    if (state == State.GAMEOVER) {
      terminating = true;
      return;
    }
    state = State.INGAME;
    gameStartTick = Game.time().now();
    enemyCount = 0;
    towerEntity = new TowerEntity(tower);
    Game.loop().perform(600, () -> {
      Game.screens().display("main");
      Game.window().getRenderComponent().fadeIn(500);
      Utils.spawn("tower", towerEntity);
    });
  }

  private static boolean spawnTick = false;

  public static void update() {
    if (state != State.INGAME) return;
    if (timing()) {
      if (!towerEntity.isDead() && spawnTick) {
        Optional.ofNullable(towerEntity.getSoldierEntity())
                .ifPresent(e -> Utils.spawn("respawn", e));
      }
      if (enemyCount < MAX_ENEMY_COUNT) {
        Utils.spawn("spawn", new EnemyEntity());
        enemyCount++;
      }
      spawnTick = !spawnTick;
    }
    if (towerEntity.isDead()
        || enemyCount >= MAX_ENEMY_COUNT && Game.world().environment().getCombatEntities().size() == 1) {
      state = State.GAMEOVER;
    }
  }

  private static boolean timing() {
    return Game.time().since(gameStartTick) > 30 && Game.loop().getTicks() % 30 == 0;
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
