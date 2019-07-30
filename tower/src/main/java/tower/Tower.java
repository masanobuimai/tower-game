package tower;

import java.util.List;

public abstract class Tower {
  public static final int MAX_SOLDIER_COUNT = 40;

  public enum State {
    READY("準備中"),
    INGAME("プレイ中"),
    GAMEOVER("ゲームオーバ");

    private String title;

    State(String t) { title = t; }

    @Override
    public String toString() {
      return super.toString() + "[" + title + "]";
    }
  }

  public TowerInfo info() {
    return new TowerInfo();
  }

  public abstract List<Soldier> getSoldierList();
}
