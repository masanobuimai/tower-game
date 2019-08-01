package tutorial.step4.ex;

import tower.EarthShake;

public interface CheckedEarthShake extends EarthShake {
  @Override
  default void shake(Ground ground) {
    checkedShake(new CheckedGround(ground));
  }

  void checkedShake(CheckedGround ground);
}
