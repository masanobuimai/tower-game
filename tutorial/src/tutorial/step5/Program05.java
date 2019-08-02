package tutorial.step5;

import tower.BasicTower;
import tower.Soldier;
import tower.TowerGame;
import tutorial.step4.MyTower;

public class Program05 {

  public static void main(String[] args) {
    m3();
  }

  private static void m1() {
    TowerGame.start(MyTower::new);
  }

  // タワーを使い回すと，兵士は死んだまま
  private static BasicTower tower;

  private static void m2() {
    tower = new BasicTower();
    tower.setSoldier(new Soldier());
    TowerGame.start(() -> tower);
  }

  private static void m3() {
    TowerGame.start(() -> {
      BasicTower tower = new BasicTower();
      tower.setSoldier(new Soldier());
      return tower;
    });
  }

}
