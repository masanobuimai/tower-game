package tutorial.step6;

import tower.Tower;
import tower.TowerGame;
import tower.TowerInfo;
import tutorial.step5.MyTower;

public class Program06 {
  public static void main(String[] args) {
    Tower tower = new MyTower();
    TowerGame.start(tower);

    TowerInfo towerInfo = tower.info();
    System.out.println("tower.State() = " + towerInfo.getState());
    System.out.println("tower.Score() = " + towerInfo.score());
    System.out.println("tower.Name() = " + towerInfo.getName());
    System.out.println("tower.Life() = " + towerInfo.getLife() + "/" + towerInfo.getMaxLife());
    System.out.println("tower.Soldier() = " + towerInfo.getSoliderCount() + "/" + towerInfo.getDeadSoliderCount() + "/" + Tower.MAX_SOLDIER_COUNT + "/" + tower.getSoldierList().size());
    System.out.println("tower.Enemy() = " + towerInfo.getEnemyCount() + "/" + towerInfo.getMaxEnemyCount());
  }
}
