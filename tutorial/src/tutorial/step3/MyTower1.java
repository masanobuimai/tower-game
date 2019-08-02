package tutorial.step3;

import tower.Soldier;
import tower.Tower;

import java.util.Arrays;
import java.util.List;

public class MyTower1 extends Tower {
  @Override
  public List<Soldier> getSoldierList() {
    return Arrays.asList(new Soldier(), new Soldier(), new Soldier());
  }
}
