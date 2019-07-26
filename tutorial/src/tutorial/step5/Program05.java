package tutorial.step5;

import tower.Tower;
import tower.TowerGame;

public class Program05 {
  public static void main(String[] args) {
    Tower tower = new MyTower();
    TowerGame.start(tower);
  }
}
