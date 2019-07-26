package tutorial.step2;

import tower.BasicTower;
import tower.TowerGame;

// 既存のタワーを使って，インスタンスを生成してみる
// 兵士がいないのですぐ死ぬ
public class Program02 {
  public static void main(String[] args) {
    BasicTower tower = new BasicTower();
    tower.setMaxLife(10);
    TowerGame.start(tower);
  }
}
