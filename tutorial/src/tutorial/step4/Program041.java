package tutorial.step4;

import tower.Tower;
import tower.TowerGame;

// 既存のタワーを使うバージョン
public class Program041 {
  public static void main(String[] args) {
    Tower tower = new MyTower1();
    TowerGame.start(tower);
  }
}
