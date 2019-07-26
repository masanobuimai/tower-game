package tower;

import java.util.List;

public abstract class Tower {
  public static final int MAX_SOLDIER_COUNT = 40;

  private final String name = "bunker";

  public String getName() {
    return name;
  }

  public abstract List<Soldier> getSoldierList();
}
