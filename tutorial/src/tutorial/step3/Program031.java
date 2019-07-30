package tutorial.step3;

import tower.Tower;
import tower.TowerGame;

public class Program031 {
  public static void main(String[] args) {
    Tower tower = new MyTower1();
    TowerGame.start(tower);
  }
}
