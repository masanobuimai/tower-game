package tutorial.step3;

import tower.Tower;
import tower.TowerGame;

// 既存のタワーを使うバージョン
public class Program03 {
  public static void main(String[] args) {
    Tower tower = new MyTower();
    TowerGame.start(tower);
  }
}
