package tutorial.step2;

import tower.BasicTower;
import tower.TowerGame;

// 既存のタワーを使うバージョン
public class Program02 {
  public static void main(String[] args) {
    BasicTower tower = new BasicTower();
    TowerGame.start(tower);
  }
}
