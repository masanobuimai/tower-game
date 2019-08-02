package tutorial.step3;

import tower.Soldier;
import tower.Tower;
import tower.TowerGame;

public class Program03 {
  public static void main(String[] args) {
//    Tower tower = new MyTower1();
    Tower tower = new MyTower2();
//    Tower tower = new MyTower3();
    TowerGame.start(tower);

    for (Soldier soldier : tower.getSoldierList()) {
      System.out.println("soldier = " + soldier);
    }
  }
}
