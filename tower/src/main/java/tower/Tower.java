package tower;

import java.util.List;

public abstract class Tower {
  private final String name = "bunker";

  public String getName() {
    return name;
  }

  public abstract List<Soldier> getSoldierList();
}
