package tutorial.step4;

import tower.Tower;
import tower.TowerGame;

public class Program04 {
  public static void main(String[] args) {
    Tower tower = new MyTower();
    TowerGame.start(tower);
  }
}
