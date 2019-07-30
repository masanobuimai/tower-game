package tutorial.step2;

import tower.BasicTower;
import tower.Soldier;
import tower.TowerGame;

public class Program02 {
  public static void main(String[] args) {
    BasicTower tower = new BasicTower();
    // TODO タワーの見た目くらい変えれるようにしたい
    tower.setMaxLife(10);

    Soldier soldier = new Soldier();
//    soldier.speedType();
//    soldier.powerType();
    tower.setSoldier(soldier);

    TowerGame.start(tower);
  }
}
