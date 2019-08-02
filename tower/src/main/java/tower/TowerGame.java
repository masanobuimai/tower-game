package tower;

import tower.engine.GM;

import java.lang.reflect.Field;
import java.util.function.Supplier;

/**
 * タワーゲームのゲームマネージャです。
 * <ul>
 *   <li>スペースキーでゲーム開始します。
 *   <li>ゲームオーバ中にESCキーでゲームを終了します。
 * </ul>
 */
public class TowerGame {
  /**
   * とりあえずゲームを開始します（タワーはありません）。
   */
  public static void start() {
    try {
      // エグい方法でライフ0のタワーを作る
      BasicTower tower = new BasicTower();
      Field field = BasicTower.class.getDeclaredField("maxLife");
      field.setAccessible(true);
      field.set(tower, 0);
      GM.start(tower);
    } catch (Exception ignore) {}
  }

  /**
   * 指定したタワーを使ってゲームを開始します。
   * @param tower タワー
   */
  public static void start(Tower tower) {
    GM.start(tower);
  }

  /**
   * ゲームを開始します。
   * ゲームオーバーになっても，スペースキーを押すと
   * 新しいタワーを使ってゲームを再開します。
   * @param towerSupplier タワーの供給手段
   */
  public static void start(Supplier<Tower> towerSupplier) {
    GM.start(towerSupplier);
  }

}
