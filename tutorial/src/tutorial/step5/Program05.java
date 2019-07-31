package tutorial.step5;

import tower.TowerGame;
import tutorial.step4.MyTower;

public class Program05 {
  public static void main(String[] args) {
    TowerGame.start(MyTower::new);
  }
}
