package tower;

import tower.engine.GM;

// TODO MapとListの集合体のほうがよかったかも...
public final class TowerInfo {
  TowerInfo() {}

  public Tower.State getState() {
    return GM.getState();
  }

  public String getName() {
    return GM.tower() != null ? GM.tower().getName() : "未定義";
  }

  public int getLife() {
    return GM.tower() != null ? GM.tower().getHitPoints().getCurrentValue() : -1;
  }

  public int getMaxLife() {
    return GM.tower() != null ? GM.tower().getHitPoints().getMaxValue() : -1;
  }

  public int getSoliderCount() {
    return GM.tower() != null ? GM.tower().getSoldierCount() : -1;
  }

  public int getDeadSoliderCount() {
    return GM.tower() != null ? GM.tower().getDeadSoldierCount() : -1;
  }

  public int getEnemyCount() {
    return GM.getEnemyCount();
  }

  public int getMaxEnemyCount() {
    return GM.MAX_ENEMY_COUNT;
  }

  public int score() {
    return GM.tower() != null ? GM.tower().score() : 0;
  }
}
