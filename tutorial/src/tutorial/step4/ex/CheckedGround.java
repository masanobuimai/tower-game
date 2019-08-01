package tutorial.step4.ex;

import tower.EarthShake;
import tower.TowerException;

public class CheckedGround {
  private EarthShake.Ground ground;

  public CheckedGround(EarthShake.Ground ground) {
    this.ground = ground;
  }

  public void shake() throws CheckedGroundException {
    try {
      ground.shake();
    } catch (TowerException e) {
      throw new CheckedGroundException("失敗した", e);
    }
  }
}
