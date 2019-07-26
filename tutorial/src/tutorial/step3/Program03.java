package tutorial.step3;

import tower.Tower;
import tower.TowerGame;

// 既存のタワー継承してクラスを作ってみよう
// BasicTowerは兵士をひとりしか召し抱えられないのですぐ死ぬ
public class Program03 {
  public static void main(String[] args) {
    // TODO この前に兵士を設定する setter を用意して茶番を演じてもいい
    Tower tower = new MyBasicTower();
    TowerGame.start(tower);
  }
}
