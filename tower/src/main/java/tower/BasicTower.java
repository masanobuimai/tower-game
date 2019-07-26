package tower;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BasicTower extends Tower {
  // TODO なんか面白味に欠ける

  @Override
  public final List<Soldier> getSoldierList() {
    if (getSoldier() != null) {
      return Stream.of(getSoldier()).collect(Collectors.toList());
    } else {
      return Collections.emptyList();
    }
  }

  protected Soldier getSoldier() {
    return null;
  }
}
