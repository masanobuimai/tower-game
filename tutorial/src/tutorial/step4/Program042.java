package tutorial.step4;

import tower.Tower;
import tower.TowerGame;

// 自分用のタワーを作って，兵士をたくさん召し抱える
// 兵士の種類を簡単にするためにクラスで分ける
public class Program042 {
  public static void main(String[] args) {
    Tower tower = new MyTower2();
    TowerGame.start(tower);
  }
}
