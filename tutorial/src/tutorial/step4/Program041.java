package tutorial.step4;

import tower.Tower;
import tower.TowerGame;

// 自分用のタワーを作って，兵士をたくさん召し抱える
// -抽象クラスのサブクラスを作る
// -コレクションのとっかかり
public class Program041 {
  public static void main(String[] args) {
    Tower tower = new MyTower1();
    TowerGame.start(tower);
  }
}
