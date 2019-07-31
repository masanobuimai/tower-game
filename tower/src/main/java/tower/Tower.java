package tower;

import java.util.List;

public abstract class Tower {
  public static final int MAX_SOLDIER_COUNT = 40;

  public abstract List<Soldier> getSoldierList();
}
