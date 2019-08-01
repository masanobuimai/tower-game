package tutorial.step4;

import tower.Tower;
import tower.TowerGame;
import tutorial.step4.ex.MyTowerEx;

public class Program04 {
  public static void main(String[] args) {
    Tower tower = new MyTowerEx();
    TowerGame.start(tower);
  }
}
