package tutorial.step4;

import tower.Tower;
import tower.TowerGame;

// 既存のタワーを使うバージョン
public class Program042 {
  public static void main(String[] args) {
    Tower tower = new MyTower2();
    TowerGame.start(tower);
  }
}
